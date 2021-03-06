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

package cz.cvut.veselj57.dt.graphql.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import cz.cvut.veselj57.dt.graphql.model.TripQL
import cz.cvut.veselj57.dt.graphql.model.toGQL


import cz.cvut.veselj57.dt.repository.TripDAO
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.runBlocking
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.getKoin


/**
 * Provide Search options for book data
 */
class TripQuery(
    private val tripDAO: TripDAO
) : Query {

    @GraphQLDescription("Return a Trips by id list")
    @Suppress("unused")
    fun searchTrips(dfe: DataFetchingEnvironment, ids: List<String>? = null): List<TripQL>  = runBlocking {
        tripDAO.get(ids).map { it.toGQL(dfe)}
    }
}

