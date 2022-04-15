package cz.cvut.veselj57.dt.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import cz.cvut.veselj57.dt.repository.HotelDAO
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.koin.ktor.ext.inject
import java.util.*


@kotlinx.serialization.Serializable
data class AuthenticatedUser(
    val email: String,
    val token: String
)


object JWTToken{

    const val CLAIM_HOTEL_ID = "hotel_id"

    fun generate(config: ApplicationConfig, id: String): String {
        val jwtSecret = config.property("jwt.secret").getString()


        val token = JWT.create()
            .withClaim(CLAIM_HOTEL_ID, id)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000*10000))
            .sign(Algorithm.HMAC256(jwtSecret))

        return token
    }

}

fun Application.configureSecurity() {

    val hotelDAO by inject<HotelDAO>()

    authentication() {
        jwt("auth-jwt") {
            val jwtSecret = this@configureSecurity.environment.config.property("jwt.secret").getString()
            val jwtDomain = this@configureSecurity.environment.config.property("jwt.domain").getString()

            realm = jwtDomain
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withIssuer()
                    .build()
            )
            validate { credential ->
                val hotelId = credential.payload.getClaim(JWTToken.CLAIM_HOTEL_ID).asString()

                return@validate hotelDAO.getHotel(hotelId)

            }
        }
    }

}
