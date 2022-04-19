package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.repository.TripDAO
import cz.cvut.veselj57.dt.services.ServerConfig
import graphql.schema.DataFetchingEnvironment
import org.koin.java.KoinJavaComponent.getKoin



@kotlinx.serialization.Serializable
@GraphQLName("Trip")
data class TripQL(
    var _id: String,
    var hotel_id: String?,
    var title: String?,
    var text: String?,
    var imgs: List<String>?,
    var tags: List<String>?,
    var img_urls: List<String>? = null,
) {

    fun main_img_url(): String {
        val baseURL = getKoin().get<ServerConfig>().baseUrl

        return baseURL + "/img/${imgs?.first()}"
    }

    fun img_urls(): List<String> {
        val baseURL = getKoin().get<ServerConfig>().baseUrl

        return imgs?.map { "$baseURL/img/$it" } ?: listOf()
    }


    suspend fun categories(dfe: DataFetchingEnvironment): List<TripCategoryQL> {
        return getKoin().get<TripDAO>().getCategories(_id).map { it.toGQL(dfe) }
    }

}

fun TripEntity.toGQL(dfe: DataFetchingEnvironment): TripQL {
    return TripQL(_id, hotel_id, title, text, imgs, tags)
}
