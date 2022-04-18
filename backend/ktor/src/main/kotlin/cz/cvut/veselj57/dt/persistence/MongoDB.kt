package cz.cvut.veselj57.dt.persistence

import com.mongodb.reactivestreams.client.gridfs.GridFSBucket
import com.mongodb.reactivestreams.client.gridfs.GridFSBuckets
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripEntity
import io.ktor.server.application.*
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


interface MongoDB {
    val database: CoroutineDatabase
    val hotels: CoroutineCollection<HotelEntity>
    val trips: CoroutineCollection<TripEntity>
    val posts: CoroutineCollection<TravelInfoEntity>
    val images: GridFSBucket
}

class MongoDBImpl(
    val DATABASE_NAME: String,
    val PORT: Int,
    val IP: String
) : MongoDB {

    companion object{
        public fun getFromApplicationConfig(application: Application): MongoDB = application.run {
            val host = environment.config.property("mongodb.host").getString()
            val port = environment.config.property("mongodb.port").getString().toInt()
            val database = environment.config.property("mongodb.database").getString()

            return MongoDBImpl(database, port, host)
        }
    }


    override val database = getMongoDatabase().coroutine

    override val hotels = database.getCollection<HotelEntity>()

    override val trips = database.getCollection<TripEntity>()

    override val posts = database.getCollection<TravelInfoEntity>()

    override val images: GridFSBucket = GridFSBuckets.create(database.database, "TripImages")


    private fun getURL(ip: String, port: Int) = "mongodb://$ip:$port"

    private fun getMongoDatabase() = KMongo.createClient(getURL(IP, PORT)).getDatabase(DATABASE_NAME)

}