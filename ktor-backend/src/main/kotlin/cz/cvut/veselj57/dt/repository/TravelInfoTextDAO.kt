package cz.cvut.veselj57.dt.repository

import com.mongodb.client.model.UpdateOptions
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.`in`
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.coroutine.updateOne



class TravelInfoTextDAO() : KoinComponent {

    val db by inject<MongoDB>()

    suspend fun get(ids: List<String>? = null): List<TravelInfoEntity> {
        return if (ids != null){
            db.posts.find(TravelInfoEntity::_id `in` ids).toList()
        }else{
            db.posts.find().toList()
        }
    }

    suspend fun getHotelTravelInfos(id: String): List<TravelInfoEntity> {
        return db.posts.find(TravelInfoEntity::hotel_id eq id).toList()
    }

    suspend fun getHotelTravelInfo(hotelId: String, infoId: String): TravelInfoEntity? {
        return db.posts.findOne(and(TravelInfoEntity::hotel_id eq hotelId, TravelInfoEntity::_id eq infoId))
    }


    suspend fun getTravelInfo(id: String?): TravelInfoEntity? {
        return db.posts.findOne(TravelInfoEntity::_id eq id)
    }

    suspend fun upsert(item: TravelInfoEntity): TravelInfoEntity {
        db.posts.updateOne(item,UpdateOptions().apply { upsert(true) })
        return item
    }

    suspend fun deleteInfo(info: TravelInfoEntity) {
        db.posts.deleteOneById(info._id)
    }

}