package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.repository.TripDAO
import cz.cvut.veselj57.dt.services.ServerConfig
import graphql.schema.DataFetchingEnvironment
import org.koin.core.component.get
import org.koin.java.KoinJavaComponent.getKoin



@kotlinx.serialization.Serializable
@GraphQLName("Trip")
data class TripQL(
    var _id: String,
    var hotel_id: String?,
    var title: String?,
    var text: String?,
    var imgs: List<String>,
    var tags: List<String>
):KoinEntity {

    suspend fun img_urls(dfe: DataFetchingEnvironment) = imgs.map { "${get<ServerConfig>().baseUrl}/img/${it}"}

    suspend fun categories(dfe: DataFetchingEnvironment): List<TripCategoryQL> {
        return getKoin().get<TripDAO>().getCategories(_id).map { it.toGQL(dfe) }
    }

}

fun TripEntity.toGQL(dfe: DataFetchingEnvironment): TripQL {
    return TripQL(_id, hotel_id, title, text, imgs, tags)
}
