package cz.cvut.veselj57.dt.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId


@Serializable
data class TripCategoryEntity(
    @Contextual
    @BsonId
    val _id: String =  newId<TripCategoryEntity>().toString(),
    var name: String,
    var trips_ids: List<String> = listOf()
)



