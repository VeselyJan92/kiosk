package cz.cvut.veselj57.dt.gql_model

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*


inline fun <reified T> JsonElement.decode(): T {
    return Json.decodeFromJsonElement(this)
}

suspend fun graphQLRequest(
    client: HttpClient,
    query: String,
    token: String? = null,
    variables: JsonObjectBuilder.()->Unit
): JsonElement {
    val request = buildJsonObject {
        put("query", query)
        putJsonObject("variables") {
            variables.invoke(this)
        }
    }

    val text = client.post("/graphql"){
        setBody(request.toString());

        token?.let {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }


    return Json.parseToJsonElement(text.bodyAsText())
}


suspend inline fun <reified T> graphQLRequestTyped(
    client: HttpClient,
    query: String,
    topLevelName: String,
    token: String? = null,
    noinline variables: JsonObjectBuilder.()->Unit,
): T {

    val response = graphQLRequest(client, query, token, variables)


    val data = response.jsonObject["data"]?.jsonObject?.get(topLevelName)

    if (data != null)
        return Json.decodeFromJsonElement(data)
    else
        throw Exception(response.jsonObject["errors"].toString())
}