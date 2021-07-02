package com.jetbrains.life_science.user.master.service

import com.jetbrains.life_science.user.master.entity.User
import java.security.Principal

interface UserService {

    fun getAllUsers(): List<User>

    fun getByEmail(email: String): User

    fun getById(id: Long): User

    fun update(info: UpdateDetailsInfo, user: User, principal: Principal): User

    fun deleteById(id: Long, principal: Principal)

    fun addFavourite(user: User, articleVersionId: Long, principal: Principal): User

    fun removeFavourite(user: User, articleVersionId: Long, principal: Principal)

    fun createUser(info: NewUserInfo): User

    fun updateRefreshToken(token: String, email: String)

    fun countAll(): Long
}
