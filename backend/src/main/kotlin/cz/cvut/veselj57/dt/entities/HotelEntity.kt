package cz.cvut.veselj57.dt.entities

import io.ktor.server.auth.*


@kotlinx.serialization.Serializable
data class HotelEntity(
    val _id: String,
    val hashed_password: String,
    val hotel_name: String,
    val email: String,
    val accommodation_text: String,
    val contact_phone: String,
    val contact_email: String,
    val official_website: String,
    var trip_categories: List<TripCategoryEntity>
): Principal











