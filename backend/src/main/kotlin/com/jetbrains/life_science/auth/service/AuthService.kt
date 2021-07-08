package com.jetbrains.life_science.auth.service

import com.jetbrains.life_science.auth.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface AuthService {

    fun login(authCredentials: AuthCredentials): AuthTokens

    fun register(credentials: Credentials): AuthTokens

    fun refreshTokens(refreshTokenCode: RefreshTokenCode): AuthTokens
}
