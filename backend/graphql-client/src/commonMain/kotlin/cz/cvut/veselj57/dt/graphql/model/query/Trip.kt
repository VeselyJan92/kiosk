package cz.cvut.veselj57.dt.graphql.model.query

import cz.cvut.veselj57.dt.graphql.model.TypeScriptInterface

@TypeScriptInterface
@kotlinx.serialization.Serializable

data class TripDTO(
    val _id: String? = null,
    val hotel_id: String,
    val title: String,
    val text: String,
    val imgs: List<String>,
    val tags: List<String>,
    val img_urls: List<String>? = null,
    val main_img_url: String? = null,
    val categories: List<TripCategoryDTO>? = null,
)

