package com.jetbrains.life_science.controller.auth.dto

import com.jetbrains.life_science.util.passwordRegex
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

data class AuthRequestDTO(

    @field:Email(
        message = "Email must be valid"
    )
    val email: String,

    @field:Pattern(
        regexp = passwordRegex,
        message = "Password must contain only allowed characters"
    )
    val password: String
)
