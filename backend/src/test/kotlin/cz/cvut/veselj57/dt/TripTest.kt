package cz.cvut.veselj57.dt


import cz.cvut.veselj57.dt.gql_model.*
import cz.cvut.veselj57.dt.graphql.model.TripCategoryQL
import cz.cvut.veselj57.dt.utils.TestSeeder
import io.ktor.server.testing.*
import io.ktor.util.*


import kotlin.test.*



class TripTest {

    @Test
    fun `insert and modify trip`() = testApplication {
        clear_database()

        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val registration = registerHotel(client, TestSeeder.getHotelRegistration(email, password))

        assertEquals(email, registration.email)

        val token = login(client, email, password)

        val seedImages = listOf("seed/pecka.jpg", "seed/bike.jpg", "seed/castle.jpg").map {
            "blob:" + javaClass.classLoader.getResource(it)!!.openStream().readAllBytes().encodeBase64()
        }

        val tripData = TestSeeder.getUpsertTripData(imgs = seedImages)

        var insertedTrip = upsertTrip(client, tripData, token)

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

        insertedTrip  = upsertTrip(client, tripData, token)

        assertEquals(2, insertedTrip.imgs?.size)
        assertEquals(newTitle, insertedTrip.title, )

        val newImg = "blob:" + javaClass.classLoader.getResource("seed/pecka.jpg")!!.openStream().readAllBytes().encodeBase64()

        tripData.imgs = tripData.imgs + newImg

        insertedTrip  = upsertTrip(client, tripData, token)


        assertEquals(3, insertedTrip.imgs?.size)

        oldImages.forEach {
            assertContains(insertedTrip.imgs!!, it)
        }


    }

    @Test
    fun `prevent trip insertion to another hotel`() = testApplicationClearDB {
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        registerHotel(client, TestSeeder.getHotelRegistration(email, password))
        val token = login(client, email, password)

        val tripCategories = modifyCategories(client, listOf(TripCategoryQL(null, "category", listOf())), token).map { it._id!! }

        val inserted = upsertTrip(client, TestSeeder.getUpsertTripData(categories = tripCategories), token)

        val h2_password = "secret"
        val h2_email = "jan.vesely92@gmail.com"

        registerHotel(client, TestSeeder.getHotelRegistration(h2_email, h2_password))
        val h2_token = login(client, h2_email, h2_password)

        val tripData = TestSeeder.getUpsertTripData(id = inserted._id, categories = tripCategories)
        try {
            upsertTrip(client, tripData, h2_token)
            fail()
        }catch (e: Exception){
            assertContains(e.toString(), "Unauthorized")
        }
    }

    @Test
    fun `modify categories`() = testApplicationClearDB{
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val registration = registerHotel(client, TestSeeder.getHotelRegistration(email, password))
        val token = login(client, email, password)

        val categoriesx = listOf(
            TripCategoryQL(null, "category 1", listOf()),
            TripCategoryQL(null, "category 2", listOf())
        )

        val categories = modifyCategories(client,categoriesx, token)

        val trip1 = upsertTrip(client, TestSeeder.getUpsertTripData(categories = listOf(categories[0]._id!!)), token)
        val trip2 = upsertTrip(client, TestSeeder.getUpsertTripData(categories = listOf(categories[1]._id!!)), token)

        val insertedCategories  = getHotelData(client, registration._id, token)!!.trip_categories

        assertEquals(categoriesx.size, insertedCategories!!.size)

        insertedCategories.find { it.name == "category 1" }!!.trip_ids.apply {
            assertEquals(1, size)
            assertEquals(trip1._id, first() )
        }

        insertedCategories.find { it.name == "category 2" }!!.trip_ids.apply {
            assertEquals(1, size)
            assertEquals(trip2._id, first() )
        }

        upsertTrip(client, TestSeeder.getUpsertTripData(id = trip1._id, categories = listOf()), token)

        getHotelData(client, registration._id, token)!!.trip_categories!!.apply {
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
