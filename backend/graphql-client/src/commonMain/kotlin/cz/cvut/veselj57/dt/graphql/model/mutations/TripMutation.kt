package cz.cvut.veselj57.dt.graphql.model.mutations

@kotlinx.serialization.Serializable
data class UpsertTrip(
    var id: String?,
    var title: String,
    val text: String,
    var imgs: List<String>,
    val tags: List<String>,
    val categories: List<String>
){
    companion object{
        const val TITLE_LENGTH = 30
        const val TEXT_LENGTH = 1000
        const val IMAGE_LENGTH = 8 * 1024 *1024
        const val TAG_LENGTH = 15
    }

    init {
        require(title.length < TITLE_LENGTH)
        require(text.length < TEXT_LENGTH)
        imgs.forEach { require(it.length < IMAGE_LENGTH) }
        tags.forEach { require(it.length < TAG_LENGTH) }
    }
}
