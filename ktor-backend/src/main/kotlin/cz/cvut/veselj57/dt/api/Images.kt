package cz.cvut.veselj57.dt.api

import cz.cvut.veselj57.dt.repository.ImageDAO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.APIRouteImages(){

    val imageDAO by inject<ImageDAO>()

    routing {
        get("img/{id}") {

            val img =  imageDAO.getImage(call.parameters["id"] ?: throw MissingRequestParameterException("id"))

            if (img == null)
                call.respond(HttpStatusCode.NotFound)
            else
                call.respondBytes(img, ContentType.Image.JPEG)
        }

    }


}