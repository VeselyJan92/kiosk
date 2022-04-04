package cz.cvut.veselj57.dt

import at.favre.lib.crypto.bcrypt.BCrypt
import com.thedeanda.lorem.LoremIpsum
import cz.cvut.veselj57.dt.entities.BlogPostEntity
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.repository.TripDAO
import io.ktor.util.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.newId


val id = "624837e9e377490f7e93f3e5"

class Seeder(): KoinComponent {

    val db by inject<MongoDB>()
    val tripDAO by inject<TripDAO>()



    suspend fun seedHotel(): String {

        val lorem = LoremIpsum.getInstance()


        val imgs = listOf(
            javaClass.classLoader.getResource("seed/pecka.jpg")!!.openStream().readAllBytes(),
            javaClass.classLoader.getResource("seed/skiresort.jpg")!!.openStream().readAllBytes(),
            javaClass.classLoader.getResource("seed/relax.jpg")!!.openStream().readAllBytes(),
            javaClass.classLoader.getResource("seed/bike.jpg")!!.openStream().readAllBytes(),
            javaClass.classLoader.getResource("seed/castle.jpg")!!.openStream().readAllBytes()
        )



        suspend fun getTrips() = buildList {
            repeat(4){
                add(tripDAO.insertTrip(id, lorem.getTitle(4, 9), lorem.getHtmlParagraphs(1, 2), imgs.random(), imgs.shuffled(), lorem.getWords(2, 4).split(" ")))
            }
        }


        db.hotels.insertOne(HotelEntity(
            _id = id,
            hashed_password = BCrypt.withDefaults().hash(10, "secret".toByteArray(Charsets.UTF_8)).encodeBase64(),
            email = "jan.vesely92@gmail.com",
            accommodation_text = "asdasd",
            contact_email = "asd",
            contact_phone = "asd",
            official_website = "asd",
            trip_categories = listOf(
                TripCategoryEntity(name = "Pro děti", trips_ids = getTrips()),
                TripCategoryEntity(name = "V okolí", trips_ids = getTrips()),
                TripCategoryEntity(name = "Autem", trips_ids = getTrips()),
                TripCategoryEntity(name = "Turistika", trips_ids = getTrips()),
                TripCategoryEntity(name = "Relax", trips_ids = getTrips()),
                TripCategoryEntity(name = "Po příjezdu", trips_ids = getTrips()),
            )
        ))


        repeat(4){
            db.posts.insertOne(BlogPostEntity(
                hotel_id = id,
                title = lorem.getTitle(4, 9),
                text = lorem.getHtmlParagraphs(1, 2)
            ))

        }

        return id
    }


}