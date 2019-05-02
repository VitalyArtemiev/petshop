package mirea.petshop.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import mirea.petshop.model.User
import mirea.petshop.model.UserRole
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component
import java.util.*

typealias Token = String

@Component("authService")
class AuthService {
    //@Value("\${auth.secret}")
    val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    val logger = LoggerFactory.getLogger("AuthLogger")

    /*fun createJWT(user: User, durationDays: Long): String {

    }

    fun verifyToken(token: String): Token {

    }

    //@Throws(UserAlreadyExistsException::class)
    fun registerUser(nameColumn: String, balance: String, hash: String) {

    }

    //@Throws(AuthFailedException::class)
    fun authUser(hash: String, salt: String, nameColumn: String): String {

    }

    //@Throws(TokenOutOfDateException::class, AuthFailedException::class)
    fun checkToken(AToken: String): User {

    }

    //@Throws(TokenOutOfDateException::class, AuthFailedException::class)
    fun refreshToken(RToken: String): String {

    }*/

    fun revokeToken() {

    }

    fun hasRole(role: UserRole, token: Token): Boolean {

        val jws: Jws<Claims>

        try {
            jws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)


            logger.debug(jws.body["role"] as String)
            val tokenRole = UserRole.valueOf(jws.body["role"] as String)

            if (tokenRole >= role) {
                return true
            }
            // we can safely trust the JWT
        } catch (e: JwtException) {       // (4)
            revokeToken()
            logger.debug(e.message)

            // we *cannot* use the JWT as intended by its creator
        }

        return false
    }

    private fun getExpirationTimeSecondsInTheFuture(seconds: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.SECOND, seconds)
        return calendar.time
    }

    fun authUser(user: User, password: String): Token? {
        if (BCrypt.checkpw(password, user.hash)) {
            var token = Jwts.builder()
                    .setSubject(user.ID.toString())
                    .claim("role", user.role)
                    .setIssuedAt(Date())
                    .setExpiration(getExpirationTimeSecondsInTheFuture(60 * 15))
                    .signWith(secretKey).compact()

            return token

        } else {
            return null
        }
    }
}