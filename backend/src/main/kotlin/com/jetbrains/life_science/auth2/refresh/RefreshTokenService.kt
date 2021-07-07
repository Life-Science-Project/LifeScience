package com.jetbrains.life_science.auth2.refresh

interface RefreshTokenService {

    fun updateRefreshToken(): RefreshTokenCode

}