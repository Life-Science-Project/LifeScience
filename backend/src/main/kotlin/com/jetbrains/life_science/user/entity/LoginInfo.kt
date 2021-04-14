package com.jetbrains.life_science.user.entity

interface LoginInfo {
    fun getId(): Long

    fun getUsername(): String

    fun getPassword(): String
}
