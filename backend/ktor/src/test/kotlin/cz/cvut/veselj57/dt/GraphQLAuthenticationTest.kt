package cz.cvut.veselj57.dt


import Credentials
import HotelActions
import com.mongodb.reactivestreams.client.MongoClient
import cz.cvut.veselj57.dt.graphql.model.mutations.HotelMutation
import cz.cvut.veselj57.dt.graphql.model.query.TripCategoryDTO
import org.bson.Document
import org.litote.kmongo.KMongo
import java.util.logging.Level
import java.util.logging.Logger

import kotlin.test.*


class GraphQLAuthenticationTest {



   /* @Test
    fun db(){


        Logger.getGlobal().log(Level.INFO, "Setup")
        val db = KMongo.createClient("mongodb://mongodb:27017").getDatabase("test")


        db.createCollection("xxxx")
        db.getCollection("xxxx").insertOne(Document("test", "test"))




        Logger.getGlobal().log(Level.INFO, "Done")


    }*/


    @Test
    fun `authenticated modification of categories`() = testApplicationClearDB {
        val hotelActions = HotelActions(client)

        val data = HotelMutation.RegisterHotel(
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
    fun `unauthenticated modification of categories`() = testApplicationClearDB {
        val hotelActions = HotelActions(client)

        val categories = listOf(TripCategoryDTO(null, "category_name", listOf()))

        try {
            hotelActions.modifyCategories(categories)
        }catch (e: Exception){
            assertContains(e.toString(), "Unauthorized")
            return@testApplicationClearDB
        }

        fail("Must throw and errpr")

    }

}
