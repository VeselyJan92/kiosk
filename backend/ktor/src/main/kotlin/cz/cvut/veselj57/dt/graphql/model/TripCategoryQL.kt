package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.repository.TripDAO
import graphql.schema.DataFetchingEnvironment
import org.koin.java.KoinJavaComponent.getKoin




@GraphQLName("TripCategory")
data class TripCategoryQL(
    val _id: String?,
    val name: String,
    var trip_ids: List<String>
){

    suspend fun trips(dfe: DataFetchingEnvironment): List<TripQL> {
        return getKoin().get<TripDAO>().get(trip_ids).map { it.toGQL(dfe) }
    }

}

fun TripCategoryEntity.toGQL(dfe: DataFetchingEnvironment): TripCategoryQL {
    return TripCategoryQL(_id, name, trips_ids)
}

