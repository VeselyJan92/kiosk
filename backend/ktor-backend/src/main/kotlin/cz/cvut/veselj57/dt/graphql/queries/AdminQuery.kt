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

package com.expediagroup.graphql.examples.server.ktor.schema

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.graphql.model.HotelQL


import cz.cvut.veselj57.dt.persistence.MongoDB
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject


/**
 * Provide Search options for book data
 */
class AdminQuery : Query {
    @GraphQLDescription("Return list of books based on BookSearchParameter options")
    @Suppress("unused")
    fun admin(dfe: DataFetchingEnvironment): String = runBlocking {

        val db by inject<MongoDB>(MongoDB::class.java)


        throw Exception("TEST EXP")

        return@runBlocking ""
        //
        //
        // dfe.graphQlContext.

        //db.hotels.find().toList()


      /*  when(dfe.graphQlContext.get<Role>("hotel_id")){
            is Role.Hotel -> db.hotels.find()
            is Role.HotelVisitor -> db.hotels.find()
            Role.MasterAccess ->   db.hotels.find()
        }.toList()*/
    }
}

