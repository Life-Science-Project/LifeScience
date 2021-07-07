package com.jetbrains.life_science.auth2.refresh.service

import com.jetbrains.life_science.auth2.refresh.entity.RefreshToken
import com.jetbrains.life_science.auth2.refresh.entity.RefreshTokenCode
import com.jetbrains.life_science.user.master.entity.UserCredentials

interface RefreshTokenService {

    fun updateRefreshToken(userCredentials: UserCredentials): RefreshTokenCode

    fun validateRefreshToken(userCredentials: UserCredentials, refreshTokenCode: RefreshTokenCode)

}