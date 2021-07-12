package com.jetbrains.life_science.auth.service

import com.jetbrains.life_science.controller.auth.dto.AuthRequestDTO

class AuthRequestToCredentialsAdapter(
    authRequestDTO: AuthRequestDTO
) : AuthCredentials {

    override val email: String = authRequestDTO.email

    override val password: String = authRequestDTO.password
}
