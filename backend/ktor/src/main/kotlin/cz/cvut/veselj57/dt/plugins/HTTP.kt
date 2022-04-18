package cz.cvut.veselj57.dt.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.*


fun Application.configureHTTP() {
    install(CORS) {


        allowMethod(HttpMethod.Post)

        allowHeader(HttpHeaders.Authorization)
        allowNonSimpleContentTypes = true
        anyHost()
    }
}
