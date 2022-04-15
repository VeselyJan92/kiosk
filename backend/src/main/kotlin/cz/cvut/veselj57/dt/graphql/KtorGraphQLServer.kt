/*
 * Copyright 2021 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import KtorGraphQLContextFactory
import KtorGraphQLRequestParser
import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import cz.cvut.veselj57.dt.graphql.GraphQLSchema
import io.ktor.server.request.ApplicationRequest
import org.koin.core.component.KoinComponent
import org.litote.kmongo.id.jackson.IdJacksonModule

/**
 * Helper method for how this Ktor example creates the common [GraphQLServer] object that
 * can handle requests.
 */
class KtorGraphQLServer(
    val mapper: ObjectMapper,
    requestParser: KtorGraphQLRequestParser,
    contextFactory: KtorGraphQLContextFactory,
    requestHandler: GraphQLRequestHandler
) : GraphQLServer<ApplicationRequest>(requestParser, contextFactory, requestHandler), KoinComponent{

    companion object {
        fun get(): KtorGraphQLServer {
            val mapper = jacksonObjectMapper().apply {
                registerModule(IdJacksonModule())
            }

            val requestParser = KtorGraphQLRequestParser(mapper)

            val contextFactory = KtorGraphQLContextFactory()
            val graphQL = GraphQLSchema.getGraphQL()
            val requestHandler = GraphQLRequestHandler(graphQL)

            return KtorGraphQLServer(mapper, requestParser, contextFactory, requestHandler)
        }

    }
}