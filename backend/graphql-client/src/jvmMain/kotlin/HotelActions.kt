import cz.cvut.veselj57.dt.graphql.model.mutations.*
import cz.cvut.veselj57.dt.graphql.model.query.HotelDTO
import cz.cvut.veselj57.dt.graphql.model.query.TripCategoryDTO
import cz.cvut.veselj57.dt.graphql.model.query.TripDTO
import io.ktor.client.*
import kotlinx.serialization.json.*

class HotelActions(client: HttpClient): GraphQLClient(client) {

    suspend fun registerHotel(data: HotelMutation.RegisterHotel): HotelDTO {
        return graphQLRequestTyped(
            "mutation(\$data: RegisterHotelInput!) { registerHotel(data: \$data) { _id email accommodation_text hotel_name contact_phone contact_email official_website }}",
            "registerHotel"
        ){
            putSerializable("data", data)
        }
    }

    suspend fun updateHotelSetting(data: HotelSettingsUpdate) {
        return graphQLRequestTyped(
            "mutation (\$data: HotelSettingsUpdateInput!) { updateHotelSettings(data: \$data) { _id }}",
            "updateHotelSettings"
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

    suspend fun deleteTrip(id: String): TripDTO {
        return graphQLRequestTyped(
            query = "mutation(\$id: String!) { deleteTrip(id: \$id){ _id hotel_id title text imgs tags } }",
            topLevelName = "deleteTrip",
        ){
            put("id", id)
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
                    travel_info{
                        _id
                        hotel_id
                        title
                        text
                    }
                    trip_categories {  
                        _id 
                        name
                        trip_ids
                        trips { 
                            _id
                            hotel_id
                            imgs
                            tags
                            text
                            title
                            img_urls
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

    suspend fun getTrips(ids: List<String>): List<TripDTO> {
        return graphQLRequestTyped(
            query = "query(\$ids: [String!]!) { searchTrips(ids: \$ids){ _id hotel_id imgs tags text title img_urls } }",
            topLevelName = "searchTrips",
        ){
            putJsonArray("ids"){
                ids.forEach{ add(JsonPrimitive(it)) }
            }
        }
    }



    suspend fun upsertTravelInfo(info: UpsertTravelInfo): TravelInfoDTO {
        return graphQLRequestTyped(
            query = "mutation (\$input: UpsertTravelInfoInput!) { upsertTravelInfo(input: \$input){ _id hotel_id title text } }",
            topLevelName = "upsertTravelInfo",
        ){
            put("input", Json.encodeToJsonElement(info))
        }
    }

    suspend fun deleteTravelInfo(id: String): TravelInfoDTO {
        return graphQLRequestTyped(
            query = "mutation(\$id: String!) { deleteTravelInfo(id: \$id){ _id hotel_id title text } }",
            topLevelName = "deleteTravelInfo",
        ){
            put("id", id)
        }
    }

}


