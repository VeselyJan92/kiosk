package cz.cvut.veselj57.dt.entities

import io.ktor.server.auth.*


@kotlinx.serialization.Serializable
data class HotelEntity(
    val _id: String,
    val hashed_password: String,
    var hotel_name: String,
    var logo_img_id: String,
    var intro_img_id: String,
    var email: String,
    var accommodation_text: String,
    var contact_phone: String,
    var contact_email: String,
    var official_website: String,
    var trip_categories: List<TripCategoryEntity>
): Principal











