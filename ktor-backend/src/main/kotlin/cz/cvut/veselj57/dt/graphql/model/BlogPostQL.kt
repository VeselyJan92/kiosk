package cz.cvut.veselj57.dt.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLName
import cz.cvut.veselj57.dt.entities.BlogPostEntity
import cz.cvut.veselj57.dt.graphql.directives.AuthHotelDirective
import graphql.schema.DataFetchingEnvironment

@GraphQLName("BlogPost")
data class BlogPostQL(
    var _id: String,
    var hotel_id: String,

    @AuthHotelDirective
    var title: String,

    var text: String,
)

fun BlogPostEntity.toGQL(dfe: DataFetchingEnvironment): BlogPostQL {
    return BlogPostQL(_id, hotel_id, title, text)
}