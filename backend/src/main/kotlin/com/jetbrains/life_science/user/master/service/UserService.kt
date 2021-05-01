package com.jetbrains.life_science.user.master.service

import com.jetbrains.life_science.user.master.entity.User

interface UserService {

    fun getByEmail(email: String): User

    fun getById(id: Long): User

    fun update(info: AddDetailsInfo): User

    fun deleteById(id: Long)

    fun addFavourite(user: User, articleId: Long): User

    fun removeFavourite(user: User, articleId: Long)

    fun createUser(info: NewUserInfo): User
}
