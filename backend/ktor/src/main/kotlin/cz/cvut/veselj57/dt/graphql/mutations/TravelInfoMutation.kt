package cz.cvut.veselj57.dt.graphql.mutations

import cz.cvut.veselj57.dt.graphql.KtorGraphQLContextFactory
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirective
import cz.cvut.veselj57.dt.graphql.exceptions.EntityNotFound
import cz.cvut.veselj57.dt.graphql.exceptions.UnauthorizedGraphQLRequest
import cz.cvut.veselj57.dt.graphql.model.TripInfoQL
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTravelInfo
import cz.cvut.veselj57.dt.graphql.model.toGQL
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import cz.cvut.veselj57.dt.repository.TravelInfoTextDAO
import graphql.schema.DataFetchingEnvironment
import org.litote.kmongo.newId


class TravelInfoMutation(
    private val travelInfoTextDAO: TravelInfoTextDAO
) {

    @AuthHotelDirective
    suspend fun upsertTravelInfo(
        dfe: DataFetchingEnvironment,
        input: UpsertTravelInfo
    ): TripInfoQL {
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        val info = travelInfoTextDAO.getTravelInfo(input._id)

        if (info != null && info.hotel_id != hotel._id)
            throw UnauthorizedGraphQLRequest()

        val upserted = travelInfoTextDAO.upsert(TravelInfoEntity(
            _id = input._id ?: newId<TravelInfoEntity>().toString(),
            hotel_id = hotel._id,
            title = input.title,
            text = input.text
        ))

        return upserted.toGQL(dfe)
    }

    @AuthHotelDirective
    suspend fun deleteTravelInfo(dfe: DataFetchingEnvironment, id: String): TripInfoQL {
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        val info = travelInfoTextDAO.getHotelTravelInfo(hotel._id, id ) ?: throw EntityNotFound()

        travelInfoTextDAO.deleteInfo(info)

        return info.toGQL(dfe)
    }

}
