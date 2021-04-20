package com.jetbrains.life_science.user.details.service

import com.jetbrains.life_science.user.details.entity.AddDetailsInfo
import com.jetbrains.life_science.user.details.entity.User

interface UserService {

    fun getByEmail(email: String): User

    fun getById(id: Long): User

    fun update(info: AddDetailsInfo, user: User): User

    fun delete(user: User)
}
