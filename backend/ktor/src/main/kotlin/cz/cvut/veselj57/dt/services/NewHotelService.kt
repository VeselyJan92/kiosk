package cz.cvut.veselj57.dt.services

import com.thedeanda.lorem.LoremIpsum
import cz.cvut.veselj57.dt.entities.TravelInfoEntity
import cz.cvut.veselj57.dt.entities.HotelEntity
import cz.cvut.veselj57.dt.entities.TripCategoryEntity
import cz.cvut.veselj57.dt.graphql.mutations.HotelMutation
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.repository.HotelDAO
import cz.cvut.veselj57.dt.repository.ImageDAO
import cz.cvut.veselj57.dt.repository.TripDAO
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.newId

class NewHotelService: KoinComponent {

    val auth by inject<AuthService>()

    val hotelDAO by inject<HotelDAO>()

    val tripDAO by inject<TripDAO>()

    val imgDAO by inject<ImageDAO>()

    val db by inject<MongoDB>()

    val seedImages = listOf(
        "seed/pecka.jpg",
        "seed/skiresort.jpg",
        "seed/relax.jpg",
        "seed/bike.jpg",
        "seed/castle.jpg"
    )

    suspend fun createNewHotel(
        data: HotelMutation.RegisterHotel
    ): HotelEntity? {


        if (hotelDAO.getHotelByEmail(data.email) != null){
            return null
        }

        val id = newId<HotelEntity>().toString()

        val lorem = LoremIpsum.getInstance()

        val imgs = seedImages.map {
            javaClass.classLoader.getResource(it)!!.openStream().readAllBytes()
        }

        suspend fun getTrips() = buildList {
            repeat(4){
                add(tripDAO.insertTrip(id, lorem.getTitle(4, 9), lorem.getHtmlParagraphs(1, 2), imgs.random(), imgs.shuffled(), lorem.getWords(2, 4).split(" ")))
            }
        }

        val blankImg = imgDAO.putImage(javaClass.classLoader.getResource("seed/placeholder-image.png")!!.openStream().readAllBytes())

        val hotel = HotelEntity(
            _id = id,
            hashed_password = auth.hashPassword(data.password),
            email = data.email,
            hotel_name = "TODOzzz",
            accommodation_text = lorem.getHtmlParagraphs(1, 3),
            logo_img_id = blankImg,
            intro_img_id = blankImg,
            contact_email = data.contact_email,
            contact_phone = data.contact_phone,
            official_website = data.official_website,
            trip_categories = listOf(
                TripCategoryEntity(name = "Pro děti", trips_ids = getTrips()),
                TripCategoryEntity(name = "V okolí", trips_ids = getTrips()),
                TripCategoryEntity(name = "Autem", trips_ids = getTrips()),
            )
        )

        hotelDAO.db.hotels.insertOne(hotel)


        repeat(4){
            db.posts.insertOne(TravelInfoEntity(
                hotel_id = id,
                title = lorem.getTitle(4, 9),
                text = lorem.getHtmlParagraphs(1, 2)
            ))

        }

        return hotel
    }



}