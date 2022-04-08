package cz.cvut.veselj57.dt.entities

import kotlinx.serialization.Serializable
import org.litote.kmongo.*


@Serializable
data class TripEntity(
    var _id: String = newId<TripEntity>().toString(),
    var hotel_id: String,
    var title: String,
    var text: String,
    var imgs: List<String>,
    var tags: List<String>,
)





