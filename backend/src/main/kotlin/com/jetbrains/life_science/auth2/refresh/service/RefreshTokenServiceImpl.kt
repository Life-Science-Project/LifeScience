package com.jetbrains.life_science.auth2.refresh.service

import com.jetbrains.life_science.auth2.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.auth2.refresh.factory.RefreshTokenFactory
import com.jetbrains.life_science.auth2.refresh.repository.RefreshTokenRepository
import com.jetbrains.life_science.exception.auth.ExpiredRefreshTokenException
import com.jetbrains.life_science.exception.auth.InvalidRefreshTokenException
import com.jetbrains.life_science.exception.auth.RefreshTokenNotFoundException
import com.jetbrains.life_science.user.master.entity.UserCredentials
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class RefreshTokenServiceImpl(
    val repository: RefreshTokenRepository,
    val factory: RefreshTokenFactory
) : RefreshTokenService {


    @Transactional
    override fun updateRefreshToken(userCredentials: UserCredentials): RefreshTokenCode {
        repository.deleteByUserCredentials(userCredentials)
        val token = factory.generateToken(userCredentials)
        val savedToken = repository.save(token)
        return RefreshTokenCode(savedToken.code)
    }

    override fun validateRefreshToken(userCredentials: UserCredentials, refreshTokenCode: RefreshTokenCode) {
        val refreshToken = repository.findByCode(refreshTokenCode.code) ?: throw RefreshTokenNotFoundException()
        if (refreshToken.expirationDateTime < LocalDateTime.now()) {
            throw ExpiredRefreshTokenException()
        }
        if (refreshToken.userCredentials.id != userCredentials.id) {
            throw InvalidRefreshTokenException()
        }
    }


}