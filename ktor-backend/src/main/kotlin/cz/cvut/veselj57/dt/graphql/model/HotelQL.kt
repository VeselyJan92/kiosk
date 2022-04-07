package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.graphql.GraphQLSchema.getKoin
import cz.cvut.veselj57.dt.repository.TravelInfoTextDAO
import graphql.schema.DataFetchingEnvironment


@kotlinx.serialization.Serializable
@GraphQLName("Hotel")
data class HotelQL(
    val _id: String,
    val email: String?,
    val accommodation_text: String?,
    val contact_phone: String?,
    val contact_email: String?,
    val official_website: String?,
    val trip_categories: List<TripCategoryQL>? = null,
){

    suspend fun blog_posts(dfe: DataFetchingEnvironment): List<BlogPostQL> {
        return getKoin().get<TravelInfoTextDAO>().getHotelTravelInfos(_id).map { it.toGQL(dfe) }
    }

}

fun HotelEntity.toGraphQL(dfe: DataFetchingEnvironment): HotelQL{
    val categories = trip_categories.map { it.toGQL(dfe) }

    return HotelQL(
        _id, email, accommodation_text,
        contact_phone, contact_email, official_website, categories
    )
}
