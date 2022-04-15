import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*


@kotlinx.serialization.Serializable
data class Credentials(
    val email: String,
    val password: String,
)

suspend fun HttpResponse.toJsonElement()= Json.parseToJsonElement(this.bodyAsText())

inline fun <reified T> JsonObjectBuilder.putSerializable(key: String, data: T){
    put(key, Json.encodeToJsonElement(data))
}


inline fun <reified T> JsonElement.decode(): T {
    return Json.decodeFromJsonElement(this)
}

abstract class GraphQLClient(val client: HttpClient){

    var token: String? = null

    suspend fun authorize(credentials: Credentials?): String? {


        token = client.post("login"){
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(credentials))
        }.toJsonElement().jsonObject.get("token")?.jsonPrimitive?.content

        return token
    }

    suspend fun graphQLRequest(
        query: String,
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
        query: String,
        topLevelName: String,
        noinline variables: JsonObjectBuilder.()->Unit,
    ): T {
        val response = graphQLRequest(query, variables)

        val data = response.jsonObject["data"]?.jsonObject?.get(topLevelName)

        val errors = response.jsonObject["errors"]

        if (errors != null)
            throw Exception(response.jsonObject["errors"].toString())

        if (data == null)
            throw Exception("Missing data")
        else
            return Json.decodeFromJsonElement(data)
    }


}


