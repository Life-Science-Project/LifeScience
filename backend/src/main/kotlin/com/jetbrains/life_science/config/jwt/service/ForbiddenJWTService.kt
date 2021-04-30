package com.jetbrains.life_science.config.jwt.service

import com.jetbrains.life_science.config.jwt.entity.ForbiddenJWT
import com.jetbrains.life_science.config.jwt.entity.ForbiddenJWTInfo

interface ForbiddenJWTService {

    fun findByToken(token: String): ForbiddenJWT

    fun delete(forbiddenJWT: ForbiddenJWT)

    fun existsByToken(token: String): Boolean

    fun saveIfNeed(info: ForbiddenJWTInfo)

    fun findAll(): List<ForbiddenJWT>
}
