import cz.cvut.veselj57.dt.graphql.model.mutations.TravelInfoMutation
import cz.cvut.veselj57.dt.graphql.model.query.HotelDTO
import cz.cvut.veselj57.dt.graphql.model.query.TripCategoryDTO
import io.ktor.client.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class HotelActions(client: HttpClient): GraphQLClient(client) {

    suspend fun registerHotel(data: TravelInfoMutation.RegisterHotel): HotelDTO {
        return graphQLRequestTyped(
            "mutation(\$data: RegisterHotelInput!) { registerHotel(data: \$data) { _id email accommodation_text hotel_name contact_phone contact_email official_website }}",
            "registerHotel"
        ){
            putSerializable("data", data)
        }
    }


    suspend fun modifyTripCategories(categories: List<TripCategoryDTO>): List<TripCategoryDTO> {

        val x= Json.encodeToJsonElement(categories)

        return graphQLRequestTyped(
            "mutation (\$categories: [TripCategoryInput!]!) { modifyTripCategories(categories: \$categories) { _id name trip_ids } }",
            topLevelName = "modifyTripCategories"
        ){
            put("categories", x)
        }
    }

}
