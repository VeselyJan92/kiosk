package cz.cvut.veselj57.dt


import Credentials
import HotelActions
import cz.cvut.veselj57.dt.graphql.model.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.model.query.TripCategoryDTO
import cz.cvut.veselj57.dt.utils.TestSeeder
import io.ktor.util.*


import kotlin.test.*



class TripTest {

    @Test
    fun `insert and modify trip`() = testApplicationClearDB {

        val hotelActions = HotelActions(client)

        val h1 = HotelMutation.RegisterHotel(
            email = "hotel1@example.com", password = "secret", "", "", "", ""
        )

        hotelActions.registerHotel(h1).apply {
            assertEquals(h1.email, this.email)
        }

        val h2 = HotelMutation.RegisterHotel(
            email = "hotel2@example.com",
            password = "secret",
            "", "", "", ""
        )

        hotelActions.registerHotel(h2).apply {
            assertEquals(h2.email, this.email)
        }

        hotelActions.authorize(Credentials(h2.email, h2.password))

        val seedImages = listOf("seed/pecka.jpg", "seed/bike.jpg", "seed/castle.jpg").map {
            "blob:" + javaClass.classLoader.getResource(it)!!.openStream().readAllBytes().encodeBase64()
        }

        val tripData = TestSeeder.getUpsertTripData(imgs = seedImages)

        var insertedTrip = hotelActions.upsertTrip(tripData)

        assertNotSame("", insertedTrip._id)
        assertEquals(tripData.text, insertedTrip.text)
        assertEquals(tripData.title, insertedTrip.title)
        assertEquals(tripData.tags, insertedTrip.tags)
        //assertEquals(tripData.categories, tripCategories)
        assertEquals(seedImages.size, insertedTrip.imgs?.size)

        val newTitle = "New Title"

        tripData.id = insertedTrip._id
        tripData.title = newTitle

        val oldImages = insertedTrip.imgs!!.drop(1)

        tripData.imgs = oldImages.map { "id:$it" }

        insertedTrip  = hotelActions.upsertTrip(tripData)

        assertEquals(2, insertedTrip.imgs?.size)
        assertEquals(newTitle, insertedTrip.title, )

        val newImg = "blob:" + javaClass.classLoader.getResource("seed/pecka.jpg")!!.openStream().readAllBytes().encodeBase64()

        tripData.imgs = tripData.imgs + newImg

        insertedTrip  = hotelActions.upsertTrip(tripData)


        assertEquals(3, insertedTrip.imgs?.size)

        oldImages.forEach {
            assertContains(insertedTrip.imgs!!, it)
        }

    }


    @Test
    fun `delete trip`() = testApplicationClearDB {
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val data = TestSeeder.getUpsertTripData(categories = listOf())
        val hotel = TestSeeder.getHotelRegistration(email, password)
        val credentials = Credentials(email, password)

        val hotelActions = HotelActions(client)

        hotelActions.registerHotel(hotel)
        hotelActions.authorize(credentials)

        val tripId = hotelActions.upsertTrip(data)._id

        assertEquals(1, hotelActions.getTrips(listOf(tripId)).size)

        hotelActions.deleteTrip(tripId)

        assertEquals(0, hotelActions.getTrips(listOf(tripId)).size)
    }

}
