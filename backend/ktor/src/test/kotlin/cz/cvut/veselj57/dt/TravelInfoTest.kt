package cz.cvut.veselj57.dt

import Credentials
import HotelActions
import cz.cvut.veselj57.dt.graphql.model.mutations.UpsertTravelInfo
import cz.cvut.veselj57.dt.utils.TestSeeder
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.fail


class TravelInfoTest {

    @Test
    fun `upsert, delete TravelInfo`() = testApplicationClearDB {
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"
        val title = "Random title"

        val actions = HotelActions(client)

        val hotelId = actions.registerHotel(TestSeeder.getHotelRegistration(email, password))._id

        actions.authorize(Credentials(email, password))

        suspend fun find() = actions.getHotelData(hotelId)!!.travel_info!!.find { it.title== title}

        assertNull(find())

        val inserted = actions.upsertTravelInfo(TestSeeder.getUpsertTravelInfo(title = title))

        assertNotNull(find())

        actions.deleteTravelInfo(inserted._id)

        assertNull(find())
    }




}