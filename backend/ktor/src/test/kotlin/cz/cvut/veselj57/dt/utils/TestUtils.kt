package cz.cvut.veselj57.dt

import cz.cvut.veselj57.dt.graphql.model.TripQL
import cz.cvut.veselj57.dt.persistence.DatabaseDebugUtils
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*



fun testApplicationClearDB(test: suspend ApplicationTestBuilder.()->Unit){
    testApplication {
        application { runBlocking { DatabaseDebugUtils.clear() } }
        test()
    }
}



