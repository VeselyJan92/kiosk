package cz.cvut.veselj57.dt.graphql.security

import graphql.schema.DataFetchingEnvironment
import kotlin.reflect.KClass

fun roleAllowed(dfe: DataFetchingEnvironment, vararg allowed: KClass<out GQLRole>): GQLRole {
    val role = dfe.graphQlContext.get<GQLRole>(KtorGraphQLContextFactory.ROLE_KEY)

    for (x in allowed){
        if (role::class.qualifiedName == x.qualifiedName){
            return role
        }
    }

    throw Exception("Unauthorized")
}


fun <T> rolesAllowed(dfe: DataFetchingEnvironment, vararg allowed: KClass<out GQLRole>, authorized: (GQLRole)->T ): T{
    return authorized(roleAllowed(dfe, *allowed))
}


inline fun <T, reified L: GQLRole> roleAllowed(dfe: DataFetchingEnvironment, allowed: KClass<L>, authorized: (L) -> T): T{
    return authorized(roleAllowed(dfe, allowed) as L)
}