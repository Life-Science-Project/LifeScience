package com.jetbrains.life_science.user.credentials.entity

interface LoginInfo {
    fun getId(): Long

    fun getEmail(): String

    fun getPassword(): String
}
