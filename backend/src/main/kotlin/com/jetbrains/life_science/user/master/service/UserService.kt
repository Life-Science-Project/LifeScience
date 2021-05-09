package com.jetbrains.life_science.user.master.service

import com.jetbrains.life_science.user.master.entity.User

interface UserService {

    fun getAllUsers(): List<User>

    fun getByEmail(email: String): User

    fun getById(id: Long): User

    fun update(info: UpdateDetailsInfo, user: User): User

    fun deleteById(id: Long)

    fun addFavourite(user: User, articleVersionId: Long): User

    fun removeFavourite(user: User, articleId: Long)

    fun createUser(info: NewUserInfo): User

    fun updateRefreshToken(token: String, email: String)
}
