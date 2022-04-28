package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.graphql.GraphQLSchema.getKoin
import cz.cvut.veselj57.dt.repository.TravelInfoTextDAO
import cz.cvut.veselj57.dt.services.ServerConfig
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.SerialName
import org.koin.core.component.get
import org.koin.java.KoinJavaComponent


@GraphQLName("Hotel")
data class HotelQL(
    val _id: String,
    val email: String?,
    val hotel_name: String?,
    var logo_img_id: String,
    var intro_img_id: String,
    val accommodation_text: String?,
    val contact_phone: String?,
    val contact_email: String?,
    val official_website: String?,
    val trip_categories: List<TripCategoryQL>? = null,
): KoinEntity {

    suspend fun travel_info(dfe: DataFetchingEnvironment): List<TripInfoQL> {
        return get<TravelInfoTextDAO>().getHotelTravelInfos(_id).map { it.toGQL(dfe) }
    }

    suspend fun logo_img_url(dfe: DataFetchingEnvironment) = "${get<ServerConfig>().baseUrl}/img/$logo_img_id"

    suspend fun intro_img_url(dfe: DataFetchingEnvironment) = "${get<ServerConfig>().baseUrl}/img/$intro_img_id"
}



fun HotelEntity.toGraphQL(dfe: DataFetchingEnvironment): HotelQL{
    val categories = trip_categories.map { it.toGQL(dfe) }

    return HotelQL(
        _id, email, hotel_name,logo_img_id, intro_img_id, accommodation_text,
        contact_phone, contact_email, official_website, categories
    )
}
