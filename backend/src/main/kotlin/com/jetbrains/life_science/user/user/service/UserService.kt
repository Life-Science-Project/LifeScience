package com.jetbrains.life_science.user.user.service

import com.jetbrains.life_science.user.user.entity.User

interface UserService {

    fun getAllUsers(): List<User>

    fun getByEmail(email: String): User

    fun getById(id: Long): User

    fun update(info: UpdateDetailsInfo, user: User): User

    fun deleteById(id: Long)

    fun createUser(info: NewUserInfo): User

    fun updateRefreshToken(token: String, email: String)

    fun countAll(): Long
}
