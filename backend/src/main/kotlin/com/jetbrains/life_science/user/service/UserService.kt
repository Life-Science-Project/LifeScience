package com.jetbrains.life_science.user.service

import com.jetbrains.life_science.user.entity.NewUserInfo

interface UserService {

    fun saveUser(newUserInfo: NewUserInfo)
}