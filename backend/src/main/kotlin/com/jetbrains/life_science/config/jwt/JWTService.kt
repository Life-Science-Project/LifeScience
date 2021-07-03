package com.jetbrains.life_science.config.jwt

import com.jetbrains.life_science.auth.AuthTokens
import com.jetbrains.life_science.util.getLogger
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Base64
import java.util.Date

@Component
class JWTService {

    @Value("\${jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${jwtExpiration}")
    var jwtExpirationSeconds: Int = 0

    @Value("\${refreshExpiration}")
    var refreshExpirationSeconds: Int = 0

    fun generateAuthTokens(username: String): AuthTokens {
        val jwt = generateJwtToken(username)
        val refreshToken = generateRefreshToken(username)
        return AuthTokens(jwt = jwt, refreshToken = refreshToken)
    }

    fun generateJwtToken(username: String): String {
        val date = Date()
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(date)
            .setExpiration(Date(date.time + jwtExpirationSeconds * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun generateRefreshToken(username: String): String {
        return sha256base64(jwtSecret + username + System.currentTimeMillis())
    }

    fun validateJwtToken(authToken: String): Boolean {
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

    fun getUserNameFromExpiredJwtToken(token: String): String {
        val claims = try {
            getClaimsFromToken(token)
        } catch (e: ExpiredJwtException) {
            e.claims
        }
        return claims.subject
    }

    fun getUserNameFromJwtToken(token: String): String {
        return getClaimsFromToken(token).subject
    }

    private fun getClaimsFromToken(authToken: String): Claims {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).body
    }

    private fun sha256base64(s: String): String {
        try {
            return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(s.toByteArray()))
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        }
    }

    companion object {
        private val logger = getLogger()
    }
}
