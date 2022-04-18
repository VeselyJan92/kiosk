package cz.cvut.veselj57.dt.graphql.model.mutations

@kotlinx.serialization.Serializable
data class UpsertTrip(
    var id: String?,
    var title: String,
    val text: String,
    var imgs: List<String>,
    val tags: List<String>,
    val categories: List<String>
)
