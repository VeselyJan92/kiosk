package cz.cvut.veselj57.dt.entities

import kotlinx.serialization.Serializable
import org.litote.kmongo.*


@Serializable
data class BlogPostEntity(
    var _id: String = newId<BlogPostEntity>().toString(),
    var hotel_id: String,
    var title: String,
    var text: String,
)





