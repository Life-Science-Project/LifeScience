package com.jetbrains.life_science.config.jwt

import com.jetbrains.life_science.user.repository.UserRepository
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date


@Component
class JWTProvider {

    private val logger: Logger = LoggerFactory.getLogger(JWTProvider::class.java)

    @Autowired
    lateinit var userRepository: UserRepository

    @Value("\${assm.app.jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${assm.app.jwtExpiration}")
    var jwtExpiration: Int? = 0

    fun generateJwtToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date((Date()).time + jwtExpiration!! * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
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

    fun getUserNameFromJwtToken(token: String): String {
        return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body.subject
    }
}