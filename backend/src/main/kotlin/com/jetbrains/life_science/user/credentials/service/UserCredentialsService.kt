package com.jetbrains.life_science.user.credentials.service

import com.jetbrains.life_science.user.credentials.entity.NewUserInfo
import com.jetbrains.life_science.user.credentials.entity.UserCredentials

interface UserCredentialsService {

    fun getByEmail(email: String): UserCredentials

    fun getById(id: Long): UserCredentials

    fun createUser(userInfo: NewUserInfo): UserCredentials
}
