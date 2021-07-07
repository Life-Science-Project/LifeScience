package com.jetbrains.life_science.auth2.refresh.factory

import com.jetbrains.life_science.auth2.refresh.entity.RefreshToken
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface RefreshTokenFactory {

    fun generateToken(credentials: Credentials): RefreshToken

}