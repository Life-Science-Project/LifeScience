package com.jetbrains.life_science.auth2.service

import com.jetbrains.life_science.auth.AuthRequest

class AuthRequestToCredentialsAdapter(
        authRequest: AuthRequest
) : AuthCredentials {

    override val email: String = authRequest.email

    override val password: String = authRequest.password

}