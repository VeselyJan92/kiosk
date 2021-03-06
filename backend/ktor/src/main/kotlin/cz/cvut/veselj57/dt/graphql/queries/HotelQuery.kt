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
import cz.cvut.veselj57.dt.graphql.model.HotelQL
import cz.cvut.veselj57.dt.graphql.model.toGraphQL


import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.repository.HotelDAO
import graphql.schema.DataFetchingEnvironment
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


/**
 * Provide Search options for book data
 */
class HotelQuery(
    private val  hotelDAO: HotelDAO
) : Query {

    @GraphQLDescription("Return a Hotel by id")
    @Suppress("unused")
    suspend fun searchHotels(dfe: DataFetchingEnvironment, id: String): HotelQL? {
        return hotelDAO.getHotel(id)?.toGraphQL(dfe)
    }
}




