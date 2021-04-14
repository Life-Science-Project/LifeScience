package com.jetbrains.life_science.user.entity

interface UserInfo {
    fun getId(): Long

    fun getUsername(): String

    fun getFirstName(): String?

    fun getLastName(): String?

    fun getEmail(): String?

    fun getPassword(): String

    fun getEnabled(): Boolean
}
