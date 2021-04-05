package com.jetbrains.life_science.user.service

import com.jetbrains.life_science.user.entity.UserInfo

interface UserService {

    fun saveUser(userInfo: UserInfo)
}