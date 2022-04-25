package cz.cvut.veselj57.dt.entities

import kotlinx.serialization.Contextual
import org.bson.types.Binary

@kotlinx.serialization.Serializable
data class ImageEntity(
    val _id: String,
    @Contextual
    private val binary: Binary,
) {
    constructor(_id: String, data:ByteArray): this(_id, Binary(data))

    val data: ByteArray get()= binary.data
}