package cz.cvut.veselj57.dt.api

import KtorGraphQLServer
import com.expediagroup.graphql.generator.extensions.print
import com.expediagroup.graphql.server.types.GraphQLServerResponse
import cz.cvut.veselj57.dt.graphql.GraphQLSchema
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.APIRouteGraphQL(){

    val graphql by inject<KtorGraphQLServer>()


    routing {
        authenticate("auth-jwt", optional = true) {
            post("graphql") {

                val result: GraphQLServerResponse? = graphql.execute(call.request)

                if (result != null) {
                    val json = graphql.mapper.writeValueAsString(result)

                    call.respondText(json, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid request")
                }
            }
        }

        get("sdl") {
            call.respondText(GraphQLSchema.graphQLSchema.print())
        }


    }


}