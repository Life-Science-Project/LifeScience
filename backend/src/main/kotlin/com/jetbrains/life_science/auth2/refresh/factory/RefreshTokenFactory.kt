package com.jetbrains.life_science.auth2.refresh.factory

import com.jetbrains.life_science.auth2.refresh.entity.RefreshToken
import com.jetbrains.life_science.user.master.entity.UserCredentials
import org.springframework.stereotype.Component

interface RefreshTokenFactory {

    fun generateToken(credentials: UserCredentials): RefreshToken

}