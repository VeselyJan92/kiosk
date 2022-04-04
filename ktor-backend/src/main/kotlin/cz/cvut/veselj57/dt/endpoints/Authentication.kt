package cz.cvut.veselj57.dt.endpoints

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.ModuleAuthentication() = routing {

//    post("login") {
//        val user = call.receive<User>()
//        // Check username and password
//        // ...
//        val token = JWT.create()
//            .withIssuer(issuer)
//            .withClaim("username", user.username)
//            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
//            .sign(Algorithm.HMAC256(secret))
//        call.respond(hashMapOf("token" to token))
//    }
//
//
//    post("get-info-link") {
//        val user = call.receive<User>()
//        // Check username and password
//        // ...
//        val token = JWT.create()
//            .withIssuer(issuer)
//            .withClaim("username", user.username)
//            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
//            .sign(Algorithm.HMAC256(secret))
//        call.respond(hashMapOf("token" to token))
//
//
//    }

}