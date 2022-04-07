package cz.cvut.veselj57.dt.graphql.common.model

import kotlin.js.JsExport


@kotlinx.serialization.Serializable
@JsExport
data class TripInfo(
    var _id: String,
    var hotel_id: String,
    var title: String?,
    var text: String?
)