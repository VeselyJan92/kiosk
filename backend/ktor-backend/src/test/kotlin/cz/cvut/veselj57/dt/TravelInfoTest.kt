package cz.cvut.veselj57.dt

import com.example.TripModel
import cz.cvut.veselj57.dt.gql_model.login
import cz.cvut.veselj57.dt.gql_model.registerHotel
import cz.cvut.veselj57.dt.utils.TestSeeder
import org.junit.Test


class TravelInfoTest {

    @Test
    fun `insert TravelInfo`() = testApplicationClearDB {
        val password = "secret"
        val email = "veselj57@fel.cvut.cz"


        val hotel = registerHotel(client, TestSeeder.getHotelRegistration(email, password))

        val token = login(client, email, password)


        TripModel("")



    }

}