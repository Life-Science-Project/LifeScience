package com.jetbrains.life_science.auth2.service

import com.jetbrains.life_science.auth2.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.user.master.entity.UserCredentials

interface AuthService {

    fun login(credentials: AuthCredentials): AuthTokens

    fun refreshTokens(userCredentials: UserCredentials, refreshTokenCode: RefreshTokenCode): AuthTokens
}