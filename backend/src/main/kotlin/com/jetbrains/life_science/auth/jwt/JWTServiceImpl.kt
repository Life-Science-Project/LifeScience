package com.jetbrains.life_science.auth.jwt

import com.jetbrains.life_science.exception.auth.ExpiredAccessTokenException
import com.jetbrains.life_science.exception.auth.InvalidAccessTokenException
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

    override fun validateJwtToken(authToken: String) {
        try {
            getClaimsFromToken(authToken)
        } catch (e: ExpiredJwtException) {
            throw ExpiredAccessTokenException()
        } catch (e: Exception) {
            throw InvalidAccessTokenException()
        }
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
