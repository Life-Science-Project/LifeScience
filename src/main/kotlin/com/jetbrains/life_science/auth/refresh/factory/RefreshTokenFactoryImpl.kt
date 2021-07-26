package com.jetbrains.life_science.auth.refresh.factory

import com.jetbrains.life_science.auth.refresh.entity.RefreshToken
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.util.UTCZone
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.LocalDateTime
import java.util.*

@Service
class RefreshTokenFactoryImpl : RefreshTokenFactory {

    @Value("\${jwtSecret}")
    lateinit var jwtSecret: String

    @Value("\${refreshExpiration}")
    var refreshExpirationSeconds: Int = 0

    override fun generateToken(credentials: Credentials): RefreshToken {
        return RefreshToken(
            id = 0,
            code = generateRefreshToken(credentials.email),
            credentials = credentials,
            expirationDateTime = generateExpirationTime()
        )
    }

    private fun generateExpirationTime(): LocalDateTime {
        return LocalDateTime.now(UTCZone).plusSeconds(refreshExpirationSeconds.toLong())
    }

    private fun generateRefreshToken(username: String): String {
        return sha256base64(jwtSecret + username + System.currentTimeMillis())
    }

    private fun sha256base64(s: String): String {
        try {
            return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(s.toByteArray()))
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        }
    }
}
