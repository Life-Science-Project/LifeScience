package com.jetbrains.life_science.auth.refresh.service

import com.jetbrains.life_science.auth.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.auth.refresh.factory.RefreshTokenFactory
import com.jetbrains.life_science.auth.refresh.repository.RefreshTokenRepository
import com.jetbrains.life_science.exception.auth.ExpiredRefreshTokenException
import com.jetbrains.life_science.exception.auth.InvalidRefreshTokenException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.util.UTCZone
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RefreshTokenServiceImpl(
    val repository: RefreshTokenRepository,
    val factory: RefreshTokenFactory
) : RefreshTokenService {

    @Transactional
    override fun updateRefreshToken(userCredentials: Credentials): RefreshTokenCode {
        repository.deleteByCredentials(userCredentials)
        return createRefreshToken(userCredentials)
    }

    override fun getCredentialsByRefreshToken(refreshTokenCode: RefreshTokenCode): Credentials {
        val refreshToken = repository.findByCode(refreshTokenCode.code) ?: throw InvalidRefreshTokenException()
        if (refreshToken.expirationDateTime < LocalDateTime.now(UTCZone)) {
            throw ExpiredRefreshTokenException()
        }
        return refreshToken.credentials
    }

    override fun createRefreshToken(userCredentials: Credentials): RefreshTokenCode {
        val token = factory.generateToken(userCredentials)
        val savedToken = repository.save(token)
        return RefreshTokenCode(savedToken.code)
    }
}
