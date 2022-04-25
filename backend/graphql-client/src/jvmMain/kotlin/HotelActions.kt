import cz.cvut.veselj57.dt.graphql.model.mutations.TravelInfoMutation
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTrip
import cz.cvut.veselj57.dt.graphql.model.query.HotelDTO
import cz.cvut.veselj57.dt.graphql.model.query.TripCategoryDTO
import cz.cvut.veselj57.dt.graphql.model.query.TripDTO
import io.ktor.client.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

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
        return graphQLRequestTyped(
            "mutation (\$categories: [TripCategoryInput!]!) { modifyTripCategories(categories: \$categories) { _id name trip_ids } }",
            topLevelName = "modifyTripCategories"
        ){
            put("categories", Json.encodeToJsonElement(categories))
        }
    }

    suspend fun upsertTrip(trip: UpsertTrip): TripDTO {
        return graphQLRequestTyped(
            query = "mutation (\$input: UpsertTripInput!) { upsertTrip(input: \$input){ _id hotel_id title text imgs tags } }",
            topLevelName = "upsertTrip",
        ){
            put("input", Json.encodeToJsonElement(trip))
        }
    }

    suspend fun modifyCategories(list: List<TripCategoryDTO> ): List<TripCategoryDTO> {
        return graphQLRequestTyped(
            query = "mutation (\$categories: [TripCategoryInput!]!) { modifyTripCategories(categories: \$categories) { _id name trip_ids } }",
            topLevelName = "modifyTripCategories"
        ){
            put("categories", json.encodeToJsonElement(list))
        }
    }

    suspend fun getHotelData(id: String, ): HotelDTO? {
        val query = """
            query(${'$'}hotel_id: String!) {
                searchHotels(id: ${'$'}hotel_id) {_id contact_email accommodation_text hotel_name contact_phone email official_website
                    trip_categories {  _id name trip_ids
                        trips { _id hotel_id imgs tags text title img_urls
                        
                        }
                    }
                }
            }
        """.trimIndent()

        return graphQLRequestTyped(
            query = query,
            topLevelName = "searchHotels",
        ){
            put("hotel_id", id)
        }
    }




}
