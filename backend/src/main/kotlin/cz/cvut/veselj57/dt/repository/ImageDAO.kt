package cz.cvut.veselj57.dt.repository

import cz.cvut.veselj57.dt.persistence.MongoDB
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.awaitFirst
import org.bson.types.ObjectId
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.coroutine
import java.nio.ByteBuffer


class ImageDAO() : KoinComponent {

    val db by inject<MongoDB>()

    suspend fun getImage(id: String): ByteArray? {
        val document = db.images.downloadToPublisher(ObjectId(id))

        if (document == null)
            return null

        val size = document.gridFSFile.awaitFirst().length.toInt()

        return document.coroutine.toFlow().fold(ByteBuffer.allocate(size)){ x, t -> x.put(t)}.array()
    }

    suspend fun putImage(img: ByteArray, name: String = "unspecified"): String {
        return db.images.uploadFromPublisher(name) { publisher ->
            publisher.onNext(ByteBuffer.wrap(img))
            publisher.onComplete()
        }.coroutine.toFlow().first().toHexString()
    }

    suspend fun deleteImage(id: String){
        db.images.delete(ObjectId(id))

    }


}