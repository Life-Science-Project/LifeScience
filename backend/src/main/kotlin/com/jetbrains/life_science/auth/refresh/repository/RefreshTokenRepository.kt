package com.jetbrains.life_science.auth.refresh.repository

import com.jetbrains.life_science.auth.refresh.entity.RefreshToken
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun deleteByCredentials(credentials: Credentials)

    fun findByCode(code: String): RefreshToken?
}
