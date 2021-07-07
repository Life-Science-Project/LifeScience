package com.jetbrains.life_science.auth.jwt

import com.jetbrains.life_science.util.getLogger
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTServiceImpl : JWTService {

    @Value("\${jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${jwtExpiration}")
    var jwtExpirationSeconds: Int = 0

    override fun generateJWT(username: String): JWTCode {
        val date = Date()
        val code = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(date)
            .setExpiration(Date(date.time + jwtExpirationSeconds * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
        return JWTCode(code)
    }

    override fun validateJwtToken(authToken: String): Boolean {
        try {
            getClaimsFromToken(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature -> Message: {} ", e)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token -> Message: {}", e)
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token -> Message: {}", e)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT token -> Message: {}", e)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty -> Message: {}", e)
        }
        return false
    }

    override fun getUserNameFromJwtToken(token: String): String {
        return getClaimsFromToken(token).subject
    }

    private fun getClaimsFromToken(authToken: String): Claims {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).body
    }

    companion object {
        private val logger = getLogger()
    }
}
