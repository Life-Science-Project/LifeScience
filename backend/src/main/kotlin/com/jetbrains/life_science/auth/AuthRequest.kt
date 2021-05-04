package com.jetbrains.life_science.auth

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class AuthRequest(

    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
