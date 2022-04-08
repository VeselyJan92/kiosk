package cz.cvut.veselj57.dt.graphql.mutations

import KtorGraphQLContextFactory
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirective
import cz.cvut.veselj57.dt.graphql.exceptions.EntityNotFound
import cz.cvut.veselj57.dt.graphql.exceptions.UnauthorizedGraphQLRequest
import cz.cvut.veselj57.dt.graphql.model.TripInfoQL
import cz.cvut.veselj57.dt.graphql.model.toGQL
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import cz.cvut.veselj57.dt.repository.TravelInfoTextDAO
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.Serializable
import org.litote.kmongo.newId


class TravelInfoMutation(
    private val travelInfoTextDAO: TravelInfoTextDAO
) {

    @Serializable
    data class UpsertTravelInfo(
        var id: String?,
        var title: String,
        val text: String,
    )


    @AuthHotelDirective
    suspend fun upsertTravelInfo(
        dfe: DataFetchingEnvironment,
        input: UpsertTravelInfo

    ): TripInfoQL {
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        val info = travelInfoTextDAO.getTravelInfo(input.id)

        if (info != null && info.hotel_id != hotel._id)
            throw UnauthorizedGraphQLRequest()

        val upserted = travelInfoTextDAO.upsert(TravelInfoEntity(
            _id = input.id ?: newId<TravelInfoEntity>().toString(),
            hotel_id = hotel._id,
            title = input.text,
            text = input.text
        ))

        return upserted.toGQL(dfe)
    }

    @AuthHotelDirective
    suspend fun deleteTravelInfo(dfe: DataFetchingEnvironment, id: String): TripInfoQL {
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        val info = travelInfoTextDAO.getHotelTravelInfo(id, hotel._id) ?: throw EntityNotFound()

        travelInfoTextDAO.deleteInfo(info)

        return info.toGQL(dfe)
    }

}
