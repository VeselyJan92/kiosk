package cz.cvut.veselj57.dt


import cz.cvut.veselj57.dt.gql_model.login
import cz.cvut.veselj57.dt.gql_model.modifyCategories
import cz.cvut.veselj57.dt.gql_model.registerHotel
import cz.cvut.veselj57.dt.graphql.model.TripCategoryQL
import cz.cvut.veselj57.dt.utils.TestSeeder
import io.ktor.client.statement.*
import io.ktor.server.testing.*


import kotlinx.serialization.json.*
import kotlin.test.*





class GraphQLAuthenticationTest {

    @Test
    fun `authenticated modification of cathegories`() = testApplication {
        clear_database()

        val password = "secret"
        val email = "veselj57@fel.cvut.cz"

        val registration = registerHotel(client, TestSeeder.getHotelRegistration(email, password))

        assertEquals(email, registration.email)

        val token = login(client, email, password)

        assertNotNull(token)

        val categories = listOf(TripCategoryQL(null, "category_name", listOf()))

        val modifiedCategories = modifyCategories(client, categories, token)

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
