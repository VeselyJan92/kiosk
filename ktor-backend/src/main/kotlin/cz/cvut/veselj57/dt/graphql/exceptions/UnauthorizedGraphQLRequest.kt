package cz.cvut.veselj57.dt.graphql.exceptions

import graphql.GraphQLError
import graphql.GraphQLException

class UnauthorizedGraphQLRequest: GraphQLException("Unauthorized")