package cz.cvut.veselj57.dt


import Credentials
import HotelActions
import cz.cvut.veselj57.dt.gql_model.modifyCategories
import cz.cvut.veselj57.dt.graphql.model.TripCategoryQL
import cz.cvut.veselj57.dt.graphql.model.query.TripCategoryDTO
import io.ktor.server.testing.*


import kotlin.test.*





class GraphQLAuthenticationTest {

    @Test
    fun `authenticated modification of categories`() = testApplicationClearDB {
        val hotelActions = HotelActions(client)

        val data = HotelActions.RegisterHotel(
            email = "secret",
            password = "veselj57@fel.cvut.cz",
            "", "", "", ""
        )

        val registration = hotelActions.registerHotel(data)

        assertEquals(data.email, registration.email)

        val token = hotelActions.authorize(Credentials(data.email, data.password))

        assertNotNull(token)

        val categories = listOf(TripCategoryDTO(null, "category_name", trip_ids = listOf(), trips = null))

        val modifiedCategories = hotelActions.modifyTripCategories(categories)

        assertEquals(categories.size, modifiedCategories.size)

        modifiedCategories.forEach {
            assertNotNull(it._id)
            assertNotEquals("", it._id)
        }
    }

    @Test
    fun `unauthenticated modification of categories`() = testApplication {
        clear_database()

        val categories = listOf(TripCategoryQL(null, "category_name", listOf()))

        try {
             modifyCategories(client, categories)
        }catch (e: Exception){
            assertContains(e.toString(), "Unauthorized")
            return@testApplication
        }

        fail("Must throw and errpr")

    }

}
