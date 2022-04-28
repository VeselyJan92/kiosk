package cz.cvut.veselj57.dt.graphql.mutations

import cz.cvut.veselj57.dt.graphql.KtorGraphQLContextFactory
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirective
import cz.cvut.veselj57.dt.graphql.model.TripQL
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTrip
import cz.cvut.veselj57.dt.graphql.model.toGQL
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import cz.cvut.veselj57.dt.repository.TripDAO
import graphql.GraphQLException
import graphql.schema.DataFetchingEnvironment
import java.util.Base64
import org.litote.kmongo.newId


class TripMutation(
    private val tripDAO: TripDAO
) {



    @AuthHotelDirective
    suspend fun upsertTrip(
        dfe: DataFetchingEnvironment,
        input: UpsertTrip
    ): TripQL? {
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        val oldTrip = tripDAO.getTrip(input.id)

        if (oldTrip != null && oldTrip.hotel_id != hotel._id)
            throw GraphQLException("Unauthorized")


        val oldImages = mutableListOf<String>()
        val newImages = mutableListOf<ByteArray>()

        input.imgs.forEach {
            when {
                it.startsWith("id:") -> oldImages.add(it.removePrefix("id:"))
                it.startsWith("blob:") -> newImages.add(Base64.getDecoder().decode( it.removePrefix("blob:")))
                else -> throw Exception("Wrong type: $it")
            }
        }

        val trip = tripDAO.upsertTrip(
            trip = TripEntity(input.id ?: newId<TripEntity>().toString() ,hotel._id, input.title, input.text, oldImages, input.tags),
            addImages = newImages
        )

        tripDAO.updateTripCategories(hotel, trip, input.categories)

        return trip.toGQL(dfe)
    }

    @AuthHotelDirective
    suspend fun deleteTrip(dfe: DataFetchingEnvironment, id: String): TripQL {
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        val trip = tripDAO.getTrip(id, hotel._id) ?: throw Exception("Trip not find.")

        tripDAO.deleteTrip(trip)

        return trip.toGQL(dfe)
    }

}