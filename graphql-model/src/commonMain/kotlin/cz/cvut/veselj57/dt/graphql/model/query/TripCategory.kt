package cz.cvut.veselj57.dt.graphql.model.query

import cz.cvut.veselj57.dt.graphql.model.TypeScriptInterface


@TypeScriptInterface
@kotlinx.serialization.Serializable
data class TripCategoryDTO(
    val _id: String? = null,
    val name: String,
    val trip_ids: List<String>? = null,
    val trips: List<TripDTO>? = null,
)