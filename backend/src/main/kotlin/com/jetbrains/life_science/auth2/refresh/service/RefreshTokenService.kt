package com.jetbrains.life_science.auth2.refresh.service

import com.jetbrains.life_science.auth2.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface RefreshTokenService {

    fun createRefreshToken(userCredentials: Credentials): RefreshTokenCode

    fun updateRefreshToken(userCredentials: Credentials): RefreshTokenCode

    fun validateRefreshToken(userCredentials: Credentials, refreshTokenCode: RefreshTokenCode)
}
