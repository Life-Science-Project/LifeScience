package com.jetbrains.life_science.auth.refresh.service

import com.jetbrains.life_science.auth.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface RefreshTokenService {

    fun createRefreshToken(userCredentials: Credentials): RefreshTokenCode

    fun updateRefreshToken(userCredentials: Credentials): RefreshTokenCode

    fun getCredentialsByRefreshToken(refreshTokenCode: RefreshTokenCode): Credentials
}
