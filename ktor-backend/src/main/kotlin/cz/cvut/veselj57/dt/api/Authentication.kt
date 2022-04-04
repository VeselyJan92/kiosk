package cz.cvut.veselj57.dt.api

import cz.cvut.veselj57.dt.plugins.JWTToken
import cz.cvut.veselj57.dt.repository.HotelDAO
import cz.cvut.veselj57.dt.services.AuthService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.APIRoutesAuthentication(){

    val config = environment.config

    routing {

        val auth by inject<AuthService>()
        val hotelDAO by inject<HotelDAO>()

        @kotlinx.serialization.Serializable
        data class User(val email: String,val password: String)

        @kotlinx.serialization.Serializable
        data class LoginResponse(val id: String, val token: String)

        post("login") {
            val user = call.receive<User>()

            val hotel = hotelDAO.getHotelByEmail(user.email)

            if (hotel == null){
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            if (auth.comparePassword(user.password, hotel.hashed_password)){
                call.respond(LoginResponse(hotel._id, JWTToken.generate(config, hotel._id)))
            }else{
                call.respond(HttpStatusCode.Forbidden)
            }

        }
    }


}