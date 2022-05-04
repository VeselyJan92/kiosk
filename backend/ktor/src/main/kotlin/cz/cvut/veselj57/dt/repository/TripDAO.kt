package cz.cvut.veselj57.dt.repository

import com.mongodb.client.model.Filters.and
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.coroutine.updateOne

class TripDAO(): KoinComponent {

    val db by inject<MongoDB>()

    val imageDAO by inject<ImageDAO>()

    suspend fun insertTrip(
        hotelId: String,
        title: String,
        text: String,
        introImage: ByteArray,
        otherImages: List<ByteArray>,
        tags: List<String>
    ): String {

        val inserted = buildList {
            add(introImage)
            addAll(otherImages)
        }.map {
            imageDAO.putImage(it)
        }

        val id = db.trips.insertOne(TripEntity(
            hotel_id = hotelId,
            title = title,
            text = text,
            imgs = inserted,
            tags = tags
        )).insertedId ?: throw Exception()

        return id.asString().value
    }

    suspend fun getTrip(id: String, hotelId: String): TripEntity? {
        return db.trips.findOne(and(TripEntity::_id eq id, TripEntity::hotel_id eq hotelId))
    }

    suspend fun getTrip(id: String?): TripEntity? {
        return db.trips.findOne(TripEntity::_id eq id)
    }

    suspend fun getCategories(tripId: String): List<TripCategoryEntity> {

        val hotel = db.hotels.aggregate<HotelEntity>(
            match(
                HotelEntity::trip_categories / TripCategoryEntity::trips_ids contains tripId
            )
        ).first()!!

        return hotel.trip_categories.filter { it.trips_ids.contains(tripId) }
    }

    suspend fun get(ids: List<String>? = null): List<TripEntity> {
        return if (ids != null){
            db.trips.find(TripEntity::_id `in` ids).toList()
        }else{
            db.trips.find().toList()
        }
    }


    suspend fun upsertTrip(trip: TripEntity, addImages: List<ByteArray> = listOf()): TripEntity {
        println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
        val oldTrip = getTrip(trip._id)

        val newImageIds = addImages.map {
            imageDAO.putImage(it)
        }

        trip.imgs = newImageIds + trip.imgs

        if (oldTrip == null){
            trip._id = db.trips.insertOne(trip).insertedId.asString().value
        } else{
            oldTrip.imgs.subtract(trip.imgs).forEach{
                imageDAO.deleteImage(it)
            }

            db.trips.updateOne(trip)
        }

        return trip
    }

    suspend fun updateTripCategories(hotel: HotelEntity, trip: TripEntity, categories: List<String>){
        hotel.trip_categories.forEach {
            it.trips_ids = it.trips_ids.toMutableSet().apply {
                if (categories.contains(it._id))
                    add(trip._id)
                else
                    remove(trip._id)
            }.toList()
        }

        db.hotels.updateOne(hotel)
    }

    suspend fun deleteTrip(trip: TripEntity){
        val hotel = db.hotels.findOne(HotelEntity::_id  eq trip.hotel_id)

        if (hotel != null){
            hotel.trip_categories.forEach {
                it.trips_ids = it.trips_ids.filter { it != trip._id }
            }

            db.hotels.updateOne(hotel)
        }

        db.trips.deleteOne(TripEntity::_id eq trip._id)
    }

}

