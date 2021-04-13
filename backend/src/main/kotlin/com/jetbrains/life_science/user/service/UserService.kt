package com.jetbrains.life_science.user.service

import com.jetbrains.life_science.user.entity.NewUserInfo
import com.jetbrains.life_science.user.entity.User

interface UserService {

    fun saveUser(newUserInfo: NewUserInfo)

    fun getUserByName(name: String): User

    fun getUserById(id: Long): User
}
