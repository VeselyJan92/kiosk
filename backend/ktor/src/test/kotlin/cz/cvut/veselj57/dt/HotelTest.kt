package cz.cvut.veselj57.dt


import Credentials
import HotelActions
import cz.cvut.veselj57.dt.graphql.model.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.model.query.TripCategoryDTO
import cz.cvut.veselj57.dt.utils.TestSeeder
import io.ktor.util.*


import kotlin.test.*



class HotelTest {


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


    @Test
    fun `update hotel settings`() = testApplicationClearDB {
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val hotel1Actions = HotelActions(client)

        val hotelId = hotel1Actions.registerHotel(TestSeeder.getHotelRegistration(email, password))._id
        hotel1Actions.authorize(Credentials(email, password))

        val data = TestSeeder.getHotelSettings(hotelId)

        hotel1Actions.updateHotelSetting(data)

        hotel1Actions.getHotelData(hotelId)!!.apply {
            assertEquals(data.hotel_name, hotel_name)
            assertEquals(data.contact_email, contact_email)
            assertEquals(data.contact_phone, contact_phone)
            assertEquals(data.accommodation_text, accommodation_text)
            assertEquals(data.official_website, official_website)
        }



    }


}
