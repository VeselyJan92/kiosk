package cz.cvut.veselj57.dt.graphql.mutations

import KtorGraphQLContextFactory
import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.expediagroup.graphql.server.operations.Mutation
import cz.cvut.veselj57.dt.Role
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirective
import cz.cvut.veselj57.dt.graphql.model.HotelQL
import cz.cvut.veselj57.dt.graphql.model.TripCategoryQL
import cz.cvut.veselj57.dt.graphql.model.toGQL
import cz.cvut.veselj57.dt.graphql.model.toGraphQL
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.services.NewHotelService
import graphql.schema.DataFetchingEnvironment
import org.litote.kmongo.newId


class HotelMutation(
    val db: MongoDB,
    val newHotelService: NewHotelService
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
        return newHotelService.createNewHotel(data)?.toGraphQL(dfe)
    }

}