package com.jetbrains.life_science.auth.service

import com.jetbrains.life_science.user.credentials.entity.Credentials

class UserToAuthCredentialsAdapter(user: Credentials) : AuthCredentials {
    override val email: String = user.email
    override val password: String = user.password
}
