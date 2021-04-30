package com.jetbrains.life_science.config.jwt.repository

import com.jetbrains.life_science.config.jwt.entity.ForbiddenJWT
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ForbiddenJWTRepository : JpaRepository<ForbiddenJWT, Long> {
    fun findByToken(token: String): Optional<ForbiddenJWT>

    fun existsByToken(token: String): Boolean
}
