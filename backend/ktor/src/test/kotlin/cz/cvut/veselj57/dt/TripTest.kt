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
    fun `prevent trip insertion to another hotel`() = testApplicationClearDB {
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val hotel1Actions = HotelActions(client)

        hotel1Actions.registerHotel(TestSeeder.getHotelRegistration(email, password))
        hotel1Actions.authorize(Credentials(email, password))


        val tripCategories = hotel1Actions.modifyCategories(listOf(TripCategoryDTO(null, "category", listOf()))).map { it._id!! }
        val inserted = hotel1Actions.upsertTrip(TestSeeder.getUpsertTripData(categories = tripCategories))

        val h2_password = "secret"
        val h2_email = "jan.vesely92@gmail.com"


        val hotel2Actions = HotelActions(client)

        hotel2Actions.registerHotel(TestSeeder.getHotelRegistration(h2_email, h2_password))
        hotel2Actions.authorize(Credentials(h2_email, h2_password))

        val tripData = TestSeeder.getUpsertTripData(id = inserted._id, categories = tripCategories)
        try {
            hotel2Actions.upsertTrip(tripData)
            fail()
        }catch (e: Exception){
            assertContains(e.toString(), "Unauthorized")
        }
    }

    @Test
    fun `modify categories`() = testApplicationClearDB{
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val hotel1Actions = HotelActions(client)

        val registration = hotel1Actions.registerHotel(TestSeeder.getHotelRegistration(email, password))
        hotel1Actions.authorize(Credentials(email, password))

        val categoriesx = listOf(
            TripCategoryDTO(null, "category 1", listOf()),
            TripCategoryDTO(null, "category 2", listOf())
        )

        val categories = hotel1Actions.modifyCategories(categoriesx)

        val trip1 = hotel1Actions.upsertTrip(TestSeeder.getUpsertTripData(categories = listOf(categories[0]._id!!)))
        val trip2 = hotel1Actions.upsertTrip(TestSeeder.getUpsertTripData(categories = listOf(categories[1]._id!!)))

        val insertedCategories  = hotel1Actions.getHotelData(registration._id)!!.trip_categories

        assertEquals(categoriesx.size, insertedCategories!!.size)

        insertedCategories.find { it.name == "category 1" }!!.trip_ids.apply {
            assertEquals(1, size)
            assertEquals(trip1._id, first() )
        }

        insertedCategories.find { it.name == "category 2" }!!.trip_ids.apply {
            assertEquals(1, size)
            assertEquals(trip2._id, first() )
        }

        hotel1Actions.upsertTrip(TestSeeder.getUpsertTripData(id = trip1._id, categories = listOf()))

        hotel1Actions.getHotelData(registration._id)!!.trip_categories!!.apply {
            find { it.name == "category 1" }!!.trip_ids.apply {
                assertEquals(0, size)
            }

            find { it.name == "category 2" }!!.trip_ids.apply {
                assertEquals(1, size)
                assertEquals(trip2._id, first() )
            }
        }

    }

}
