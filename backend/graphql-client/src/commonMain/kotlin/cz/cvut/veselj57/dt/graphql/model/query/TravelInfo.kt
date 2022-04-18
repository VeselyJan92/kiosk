package cz.cvut.veselj57.dt.graphql.model.query

import cz.cvut.veselj57.dt.graphql.model.TypeScriptInterface

@TypeScriptInterface
@kotlinx.serialization.Serializable
data class TravelInfoDTO(
    val _id: String? = null,
    val hotel_id: String,
    val title: String,
    val text: String,
    val hotel: HotelDTO? = null,
)



