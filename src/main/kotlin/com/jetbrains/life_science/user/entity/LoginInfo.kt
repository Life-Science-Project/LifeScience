package com.jetbrains.life_science.user.entity

interface LoginInfo {
    fun getID(): Long

    fun getUsername(): String

    fun getPassword(): String
}
