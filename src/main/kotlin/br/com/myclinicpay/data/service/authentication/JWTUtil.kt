package br.com.myclinicpay.data.service.authentication

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    private val expiration: Long = 3600000

    private val date = Date(System.currentTimeMillis() + expiration)
    fun generateToken(email: String): String {
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(date)
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        val claims = this.getClaimsToken(token)
        if (claims != null) {
            val expirationDate = claims.expiration
            val now = Date(System.currentTimeMillis())
            if (expirationDate != null && now.before(expirationDate)) {
                return true
            }
        }

        return false
    }

    fun getUserEmail(token: String): String? {
        val claims = getClaimsToken(token)
        return claims?.subject
    }

    private fun getClaimsToken(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body
        } catch (error: Exception) {
            throw Exception("Error parsing token.")
        }
    }
}
