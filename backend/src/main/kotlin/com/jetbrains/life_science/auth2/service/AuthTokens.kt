package com.jetbrains.life_science.auth2.service

import com.jetbrains.life_science.auth2.jwt.JWTCode
import com.jetbrains.life_science.auth2.refresh.RefreshTokenCode

data class AuthTokens(
    var jwt: JWTCode,
    var refreshToken: RefreshTokenCode
)
