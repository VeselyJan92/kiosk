package cz.cvut.veselj57.dt


import cz.cvut.veselj57.dt.gql_model.*
import cz.cvut.veselj57.dt.graphql.model.TripCategoryQL
import cz.cvut.veselj57.dt.graphql.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.mutations.TripMutation
import io.ktor.client.statement.*
import io.ktor.server.testing.*


import kotlinx.serialization.json.*
import org.litote.kmongo.MongoOperator
import kotlin.test.*



class TripTest {

    @Test
    fun `insert trip`() = testApplication {
        clear_database()

        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val registration = registerHotel(client, TestSeeder.getHotelRegistration(email, password))

        assertEquals(email, registration.email)

        val token = login(client, email, password)

        val data = getHotelData(client, registration._id, token)

        assertNotNull(data)

        val category = TripCategoryQL(null, "category", listOf())

        val categories = modifyCategories(client, listOf(category), token)

        assertEquals(1, categories.size)

        val tripCategories= categories.map { it._id!! }

        val tripData = TestSeeder.getUpsertTripData(categories = tripCategories)

        val insertedTrip = upsertTrip(client, tripData, token)

        assertNotSame("", insertedTrip._id)
        assertEquals(tripData.text, insertedTrip.text)
        assertEquals(tripData.title, insertedTrip.title)
        assertEquals(tripData.tags, insertedTrip.tags)
        assertEquals(tripData.categories, tripCategories)
    }

    @Test
    fun `preven modification`() = testApplication {
        clear_database()

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

}
