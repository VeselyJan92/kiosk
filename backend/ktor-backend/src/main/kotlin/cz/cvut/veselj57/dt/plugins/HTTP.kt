package cz.cvut.veselj57.dt.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.http.*


fun Application.configureHTTP() {
    install(CORS) {
        method(HttpMethod.Post)

        header(HttpHeaders.Authorization)
        allowNonSimpleContentTypes = true
        anyHost()
    }
}
