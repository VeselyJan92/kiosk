package cz.cvut.veselj57.dt.repository

import cz.cvut.veselj57.dt.entities.ImageEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.awaitFirst
import org.bson.types.ObjectId
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.newId
import java.nio.ByteBuffer
import java.util.*


class ImageDAO() : KoinComponent {

    val db by inject<MongoDB>()

    suspend fun getImage(id: String): ByteArray? {
        return  db.imgs.findOne(ImageEntity::_id eq id)?.data
    }

    suspend fun putImage(img: ByteArray, name: String = "unspecified"): String {
        require (img.size <= 16 * 1024 * 1024 * 8)

        val id = newId<ImageEntity>().toString()

        db.imgs.save(ImageEntity(id, img))

        return id
    }

    suspend fun putImageBase64(base64: String, name: String = "unspecified") = putImage(Base64.getDecoder().decode(base64), name)

    suspend fun deleteImage(id: String){
        db.imgs.deleteOneById(id)
    }


}