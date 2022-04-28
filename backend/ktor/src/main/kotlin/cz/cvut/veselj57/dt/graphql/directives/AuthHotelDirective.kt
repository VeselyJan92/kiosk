package cz.cvut.veselj57.dt.graphql.directives

import cz.cvut.veselj57.dt.graphql.KtorGraphQLContextFactory
import com.expediagroup.graphql.generator.annotations.GraphQLDirective
import com.expediagroup.graphql.generator.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveWiring
import cz.cvut.veselj57.dt.graphql.exceptions.UnauthorizedGraphQLRequest
import cz.cvut.veselj57.dt.graphql.security.GQLRole
import graphql.introspection.Introspection
import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition







@GraphQLDirective(name = "AuthorizeHotel", description = "Check if hotel was authorized",  [
    Introspection.DirectiveLocation.MUTATION,
    Introspection.DirectiveLocation.QUERY,
    Introspection.DirectiveLocation.OBJECT,
    Introspection.DirectiveLocation.FIELD_DEFINITION,
] )
annotation class AuthHotelDirective

class AuthHotelDirectiveWiring : KotlinSchemaDirectiveWiring {

    override fun onField(
        environment: KotlinFieldDirectiveEnvironment
    ): GraphQLFieldDefinition {

        val originalDataFetcher: DataFetcher<*> = environment.getDataFetcher()

        environment.setDataFetcher { dfe ->
            val role = dfe.graphQlContext.get<GQLRole>(KtorGraphQLContextFactory.ROLE_KEY)

            if (role !is GQLRole.Hotel)
                throw UnauthorizedGraphQLRequest()

            originalDataFetcher.get(dfe)
        }

        return environment.element
    }
}