package cz.cvut.veselj57.dt.repository

import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate

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







}