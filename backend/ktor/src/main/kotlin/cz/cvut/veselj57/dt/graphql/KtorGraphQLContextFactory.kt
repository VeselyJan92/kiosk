/*
 * Copyright 2022 Expedia, Inc
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


import com.expediagroup.graphql.generator.execution.GraphQLContext
import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import io.ktor.server.auth.*
import io.ktor.server.request.*
import org.koin.core.component.KoinComponent

/**
 * Custom logic for how this example app should create its context given the [ApplicationRequest]
 */
class KtorGraphQLContextFactory : GraphQLContextFactory<GraphQLContext, ApplicationRequest>, KoinComponent {

    companion object{
        val ROLE_KEY = "ROLE_KEY"
    }

    override suspend fun generateContextMap(request: ApplicationRequest): Map<Any, Any> = buildMap {

        val hotel = request.call.authentication.principal as HotelEntity?

        put(ROLE_KEY, if(hotel != null) GQLRole.Hotel(hotel._id, hotel) else GQLRole.Visitor)

    }

}
