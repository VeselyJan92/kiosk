package cz.cvut.veselj57.dt.gql_model

import cz.cvut.veselj57.dt.graphql.model.HotelQL
import cz.cvut.veselj57.dt.graphql.model.TripCategoryQL
import cz.cvut.veselj57.dt.graphql.model.TripQL
import cz.cvut.veselj57.dt.graphql.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.mutations.TravelInfoMutation
import cz.cvut.veselj57.dt.graphql.mutations.TripMutation
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*



suspend fun registerHotel(client:HttpClient, hotel: HotelMutation.RegisterHotel): HotelQL {
    return graphQLRequestTyped(
        client,
        "mutation(\$data: RegisterHotelInput!) { registerHotel(data: \$data) { _id email accommodation_text contact_phone contact_email official_website }}",
        "registerHotel"
    ){
        put("data", Json.encodeToJsonElement(hotel))
    }
}

suspend fun login(client: HttpClient, email: String, password: String): String? {
    return client.post("login"){

        val credentials = buildJsonObject{
            put("email", email)
            put("password", password)
        }.toString()

        contentType(ContentType.Application.Json)
        setBody(credentials)

    }.toJsonElement().jsonObject.get("token")?.jsonPrimitive?.content
}


suspend fun modifyCategories(
    client:HttpClient,
    list: List<TripCategoryQL>,
    token: String? = null,
): List<TripCategoryQL> {
    return graphQLRequestTyped(client,
        "mutation (\$categories: [TripCategoryInput!]!) { modifyTripCategories(categories: \$categories) { _id name trip_ids } }",
        token = token,
        topLevelName = "modifyTripCategories"
    ){
        put("categories", Json.encodeToJsonElement(list))
    }
}

suspend fun upsertTrip(
    client:HttpClient,
    trip: TripMutation.UpsertTrip,
    token: String?= null,
): TripQL {

    return graphQLRequestTyped(client,
        "mutation (\$input: UpsertTripInput!) { upsertTrip(input: \$input){ _id hotel_id title text imgs tags } }",
        topLevelName = "upsertTrip",
        token = token
    ){
        put("input", Json.encodeToJsonElement(trip))
    }
}


suspend fun upsertTravelInfo(
    client:HttpClient,
    info: TravelInfoMutation.UpsertTravelInfo,
    token: String?= null,
): TraveIn {

    return graphQLRequestTyped(client,
        "mutation (\$input: UpsertTripInput!) { upsertTrip(input: \$input){ _id hotel_id title text imgs tags } }",
        topLevelName = "upsertTrip",
        token = token
    ){
        put("input", Json.encodeToJsonElement(info))
    }
}
mutation ($input: UpsertTravelInfoInput!) { upsertTravelInfo(input: $input){ _id hotel_id title text } }

suspend fun getHotelData(
    client:HttpClient,
    id: String,
    token: String?= null,
): HotelQL? {
    val query = """
        query(${'$'}hotel_id: String!) {
            searchHotels(id: ${'$'}hotel_id) {_id contact_email accommodation_text contact_phone email official_website
                trip_categories {  _id name trip_ids
                    trips { _id hotel_id imgs tags text title img_urls
                    
                    }
                }
            }
        }
    """.trimIndent()

    return graphQLRequestTyped(client,
        query,
        topLevelName = "searchHotels",
        token = token
    ){
        put("hotel_id", id)
    }
}








