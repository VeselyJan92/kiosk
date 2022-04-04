package cz.cvut.veselj57.dt.graphql.mutations

import KtorGraphQLContextFactory
import com.expediagroup.graphql.server.operations.Mutation
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirective
import cz.cvut.veselj57.dt.graphql.model.TripQL
import cz.cvut.veselj57.dt.graphql.model.toGQL
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import cz.cvut.veselj57.dt.graphql.security.roleAllowed
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.repository.ImageDAO
import cz.cvut.veselj57.dt.repository.TripDAO
import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.GraphQLException
import graphql.GraphqlErrorException
import graphql.schema.DataFetchingEnvironment
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import java.util.Base64
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.newId


class TripMutation(
    val tripDAO: TripDAO,
    val imageDAO: ImageDAO,
    val db: MongoDB
) : Mutation {

    @kotlinx.serialization.Serializable
    data class UpsertTrip(
        val id: String?,
        val title: String,
        val text: String,
        val imgs: List<String>,
        val tags: List<String>,
        val categories: List<String>
    )

    @AuthHotelDirective
    suspend fun upsertTrip(
        dfe: DataFetchingEnvironment,
        input: UpsertTrip

    ): TripQL {
        val hotel = dfe.graphQlContext.get<GQLRole.Hotel>(KtorGraphQLContextFactory.ROLE_KEY).entity

        val oldTrip = tripDAO.getTrip(input.id)

        if (oldTrip != null && oldTrip.hotel_id != hotel._id)
            throw GraphQLException("Unauthorized")

        val img_ids = input.imgs.map {
            when{
                it.startsWith("http") -> {
                    it.substringAfterLast("/")
                }

                it.startsWith("blob") -> {
                    imageDAO.putImage(Base64.getDecoder().decode( it.removePrefix("blob:")))
                }
                else -> throw Exception("Wrong type: $it")
            }
        }

        val trip = TripEntity(input.id ?: newId<TripEntity>().toString() ,hotel._id, input.title, input.text, img_ids, input.tags)

        if (oldTrip == null){
            trip._id = db.trips.insertOne(trip).insertedId.asString().value
        } else{
            db.trips.updateOne(trip)

            oldTrip.imgs.subtract(img_ids).forEach{
                imageDAO.deleteImage(it)
            }
        }

        hotel.trip_categories.forEach {
            it.trips_ids = it.trips_ids.toMutableSet().apply {
                if (input.categories.contains(it._id))
                    add(trip._id)
                else
                    remove(trip._id)
            }.toList()
        }

        db.hotels.updateOne(hotel)

        return trip.toGQL(dfe)
    }

    suspend fun deleteTrip(dfe: DataFetchingEnvironment, id: String): TripQL = roleAllowed(dfe, GQLRole.Hotel::class){ role ->
        val trip = tripDAO.getTrip(id, role._id) ?: throw Exception("Trip not find.")
        val hotel = db.hotels.findOne(HotelEntity::_id  eq role._id) ?: throw Exception("Hotel not find.")

        hotel.trip_categories.forEach {
            it.trips_ids = it.trips_ids.toMutableList().apply { remove(trip._id) }
        }

        db.hotels.updateOne(hotel)
        db.trips.deleteOneById(ObjectId(id))

        return trip.toGQL(dfe)
    }

}