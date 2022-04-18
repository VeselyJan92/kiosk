package cz.cvut.veselj57.dt.graphql.mutations

import KtorGraphQLContextFactory
import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.expediagroup.graphql.server.operations.Mutation
import cz.cvut.veselj57.dt.Role
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirective
import cz.cvut.veselj57.dt.graphql.model.*
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.repository.HotelDAO
import cz.cvut.veselj57.dt.services.NewHotelService
import graphql.schema.DataFetchingEnvironment
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.newId

class HotelMutation(
    private val db: MongoDB,
    private val newHotelService: NewHotelService,
    private val hotelDAO: HotelDAO
) : Mutation {

    @AuthHotelDirective
    suspend fun modifyTripCategories(dfe: DataFetchingEnvironment, categories: List<TripCategoryQL>): List<TripCategoryQL> {

        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        hotel.trip_categories = categories.map {
            TripCategoryEntity(
                _id = it._id ?: newId<TripCategoryQL>().toString(),
                name = it.name,
                trips_ids = it.trip_ids
            )
        }

        db.hotels.save(hotel)

        return hotel.trip_categories.map { it.toGQL(dfe) }
    }

    data class HotelUpdate(
        val _id: String,
        val hotel_name: String,
        val accommodation_text: String,
        val contact_phone: String,
        val contact_email: String,
        val official_website: String
    )

    @AuthHotelDirective
    suspend fun modifyHotel(dfe: DataFetchingEnvironment, data: HotelUpdate): HotelQL{
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        hotel.apply {
            hotel_name          = data.hotel_name
            accommodation_text  = data.accommodation_text
            contact_phone       = data.contact_phone
            contact_email       = data.contact_email
            official_website    = data.official_website
        }

        hotelDAO.db.hotels.save(hotel)

        return hotel.toGraphQL(dfe)
    }

    @kotlinx.serialization.Serializable
    data class RegisterHotel(
        val email: String,
        val password: String,
        val accommodation_text: String,
        val contact_phone: String,
        val contact_email: String,
        val official_website: String,
    )

    suspend fun registerHotel(dfe: DataFetchingEnvironment, data: RegisterHotel): HotelQL? {
        val hotel = newHotelService.createNewHotel(data)?.toGraphQL(dfe)
        return hotel
    }

}