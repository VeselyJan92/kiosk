package cz.cvut.veselj57.dt.repository

import cz.cvut.veselj57.dt.entities.BlogPostEntity
import cz.cvut.veselj57.dt.entities.TripEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.awaitFirst
import org.bson.types.ObjectId
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import java.nio.ByteBuffer


class BlogPostDAO() : KoinComponent {

    val db by inject<MongoDB>()

    suspend fun get(ids: List<String>? = null): List<BlogPostEntity> {
        return if (ids != null){
            db.posts.find(BlogPostEntity::_id `in` ids).toList()
        }else{
            db.posts.find().toList()
        }
    }

    suspend fun getHotelBlogPosts(id: String): List<BlogPostEntity> {
        return db.posts.find(BlogPostEntity::hotel_id eq id).toList()
    }

}