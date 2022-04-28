package cz.cvut.veselj57.dt.graphql.model.mutations

import cz.cvut.veselj57.dt.graphql.model.TypeScriptInterface
import cz.cvut.veselj57.dt.graphql.model.query.HotelDTO


@TypeScriptInterface
@kotlinx.serialization.Serializable
data class TravelInfoDTO(
    val _id: String,
    val hotel_id: String,
    val title: String,
    val text: String,
   // val hotel: HotelDTO? = null,
)

@TypeScriptInterface
@kotlinx.serialization.Serializable
data class UpsertTravelInfo(
    val _id: String?,
    val title: String,
    val text: String,
)