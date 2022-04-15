package cz.cvut.veselj57.dt.repository

import cz.cvut.veselj57.dt.entities.HotelEntity
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
import java.nio.ByteBuffer


class HotelDAO() : KoinComponent {

    val db by inject<MongoDB>()

    suspend fun getHotel(id: String): HotelEntity? {

        return db.hotels.findOne(HotelEntity::_id eq id)
    }

    suspend fun getHotelByEmail(email: String): HotelEntity? {
        return db.hotels.findOne(HotelEntity::email eq email)
    }


}