package com.jetbrains.life_science.controller.auth.dto

import com.jetbrains.life_science.util.passwordRegex
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

data class NewUserDTO(

    @field:Pattern(
        regexp = nameRegexp,
        message = "First name must contain only allowed characters"
    )
    val firstName: String,

    @field:Pattern(
        regexp = nameRegexp,
        message = "Last name must contain only allowed characters"
    )
    val lastName: String,

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

const val nameRegexp = "^[a-zA-Z]{2,30}$"
