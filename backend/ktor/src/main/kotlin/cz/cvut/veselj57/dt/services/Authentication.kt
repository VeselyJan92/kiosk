package cz.cvut.veselj57.dt.services

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.util.*
import kotlin.text.toCharArray


class AuthService  {

    fun comparePassword(password: String, hash: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hash.decodeBase64String()).verified
    }

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hash(10, password.toByteArray(Charsets.UTF_8)).encodeBase64()
    }

}
