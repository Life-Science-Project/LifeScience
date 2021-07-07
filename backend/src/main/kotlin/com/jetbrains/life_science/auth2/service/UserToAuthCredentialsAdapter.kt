package com.jetbrains.life_science.auth2.service

import com.jetbrains.life_science.user.master.entity.User

class UserToAuthCredentialsAdapter(user: User) : AuthCredentials {
    override val email: String = user.email
    override val password: String = user.password
}