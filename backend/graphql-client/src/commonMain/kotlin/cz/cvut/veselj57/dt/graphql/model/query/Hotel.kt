package cz.cvut.veselj57.dt.graphql.model.query

import cz.cvut.veselj57.dt.graphql.model.TypeScriptInterface

@TypeScriptInterface
@kotlinx.serialization.Serializable
data class HotelDTO (
    val _id: String,
    val email: String?,
    val accommodation_text: String?,
    val hotel_name: String?,
    val contact_phone: String?,
    val contact_email: String?,
    val official_website: String? = null,
    val trip_categories: List<TripCategoryDTO>? = null,
    val travel_info: List<TravelInfoDTO>? = null,

    )
