package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.repository.HotelDAO
import cz.cvut.veselj57.dt.repository.TripDAO
import graphql.schema.DataFetchingEnvironment
import org.koin.java.KoinJavaComponent.getKoin



@GraphQLName("TripInfo")
class TripInfoQL(
    var _id: String,
    var hotel_id: String,
    var title: String?,
    var text: String?
) {

    suspend fun hotel(dfe: DataFetchingEnvironment): HotelQL? {
        return getKoin().get<HotelDAO>().getHotel(hotel_id)?.toGraphQL(dfe)
    }

}


fun TravelInfoEntity.toGQL(dfe: DataFetchingEnvironment): TripInfoQL {
    return TripInfoQL(_id, hotel_id, title, text)
}
