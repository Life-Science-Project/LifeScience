package com.jetbrains.life_science.auth2.refresh.repository

import com.jetbrains.life_science.auth2.refresh.entity.RefreshToken
import com.jetbrains.life_science.user.master.entity.UserCredentials
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun deleteByUserCredentials(credentials: UserCredentials)

    fun findByCode(code: String): RefreshToken?
}