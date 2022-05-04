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

package cz.cvut.veselj57.dt.graphql

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.generator.scalars.IDValueUnboxer
import com.expediagroup.graphql.generator.toSchema
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirectiveWiring
import cz.cvut.veselj57.dt.graphql.directives.StringRangeDirectiveWiring
import cz.cvut.veselj57.dt.graphql.model.KoinEntity
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTrip
import cz.cvut.veselj57.dt.graphql.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.mutations.TravelInfoMutation
import cz.cvut.veselj57.dt.graphql.mutations.TripMutation
import cz.cvut.veselj57.dt.graphql.queries.HotelQuery
import cz.cvut.veselj57.dt.graphql.queries.TripQuery
import graphql.*
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.reflect.KClass

private class CustomDataFetcherExceptionHandler : DataFetcherExceptionHandler {
    override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {

        val exception = if (handlerParameters.exception.cause is IllegalArgumentException){
            handlerParameters.exception.cause!!
        }else
            handlerParameters.exception

        exception.printStackTrace()


        val error = object : GraphQLError{
            override fun getMessage() =  exception.message +  ": " +  handlerParameters.path.toString()

            override fun getLocations() = mutableListOf(handlerParameters.sourceLocation)

            override fun getErrorType() = ErrorType.DataFetchingException

        }
        return DataFetcherExceptionHandlerResult.newResult().error(error).build()
    }
}

object GraphQLSchema: KoinComponent {

    private val config = SchemaGeneratorConfig(
        supportedPackages = listOf(
            "cz.cvut.veselj57.dt.graphql",
            UpsertTrip::class.qualifiedName!!
        ),
        hooks = object : SchemaGeneratorHooks {
            override val wiringFactory = KotlinDirectiveWiringFactory(
                manualWiring = buildMap {
                    put("AuthorizeHotel",  AuthHotelDirectiveWiring())
                    put("StringRange",  StringRangeDirectiveWiring())
                },
            )

            override fun isValidSuperclass(kClass: KClass<*>): Boolean {
                return if (kClass == KoinComponent::class || kClass == KoinEntity::class)
                    false
                else
                    super.isValidSuperclass(kClass)
            }
        },
    )

    private val queries = listOf(
        TopLevelObject(HotelQuery(get())),
        TopLevelObject(TripQuery(get())),
    )
    private val mutations = listOf(
        TopLevelObject(TripMutation(get())),
        TopLevelObject(HotelMutation(get(), get(), get(), get ())),
        TopLevelObject(TravelInfoMutation(get()))
    )

    val graphQLSchema = toSchema(config, queries, mutations)


    fun getGraphQL(): GraphQL {
        return GraphQL.newGraphQL(graphQLSchema).valueUnboxer(IDValueUnboxer())
            .defaultDataFetcherExceptionHandler(CustomDataFetcherExceptionHandler())
            .build()
    }
}
