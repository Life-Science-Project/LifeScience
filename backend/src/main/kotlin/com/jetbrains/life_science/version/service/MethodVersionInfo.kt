package com.jetbrains.life_science.version.service

import com.jetbrains.life_science.user.entity.User

interface MethodVersionInfo {

    val methodId: Long

    val name: String

    val user: User
}
