package cz.cvut.veselj57.dt


import cz.cvut.veselj57.dt.api.APIRouteGraphQL
import cz.cvut.veselj57.dt.api.APIRouteImages
import cz.cvut.veselj57.dt.api.APIRoutesAuthentication
import cz.cvut.veselj57.dt.graphql.GraphQLSchema.getKoin
import cz.cvut.veselj57.dt.persistence.MongoDB
import cz.cvut.veselj57.dt.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import java.util.*

import com.mongodb.diagnostics.logging.Loggers;
import cz.cvut.veselj57.dt.persistence.DatabaseDebugUtils
import org.koin.ktor.ext.get
import org.slf4j.event.Level
import java.util.logging.Level.OFF

import java.util.logging.Level.SEVERE
import java.util.logging.Logger;


fun main(args: Array<String>){
    io.ktor.server.cio.EngineMain.main(args)
}


fun Application.main() {

    //configureRouting()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureKoin()


/*
    runBlocking {
        val db = get<MongoDB>()

        db.hotels.deleteMany()
        db.trips.deleteMany()
        db.images.drop()
        db.posts.drop()

        Seeder().seedHotel()
    }
*/




    APIRoutesAuthentication()
    APIRouteImages()
    APIRouteGraphQL()
}


























