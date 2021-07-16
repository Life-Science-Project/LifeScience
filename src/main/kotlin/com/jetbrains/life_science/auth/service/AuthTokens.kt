package com.jetbrains.life_science.auth.service

import com.jetbrains.life_science.auth.jwt.JWTCode
import com.jetbrains.life_science.auth.refresh.entity.RefreshTokenCode

data class AuthTokens(
    var jwt: JWTCode,
    var refreshToken: RefreshTokenCode
)
