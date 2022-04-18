package cz.cvut.veselj57.dt.graphql.model.mutations

import cz.cvut.veselj57.dt.graphql.model.TypeScriptInterface

interface HotelMutation {

    @kotlinx.serialization.Serializable
    @TypeScriptInterface
    data class TripCategory(
        val _id: String?,
        val name: String,
        var trip_ids: List<String>
    )

}