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
    Introspection.DirectiveLocation.FIELD_DEFINITION,
] )
annotation class StringRangeDirective(val max: Int = 100)

class StringRangeDirectiveWiring : KotlinSchemaDirectiveWiring {

    override fun onField(
        environment: KotlinFieldDirectiveEnvironment
    ): GraphQLFieldDefinition {

        environment.directive.arguments.get(0).argumentValue.toString().toInt()

        return environment.element
    }
}