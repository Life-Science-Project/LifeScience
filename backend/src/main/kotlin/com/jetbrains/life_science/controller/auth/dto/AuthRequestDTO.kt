package com.jetbrains.life_science.controller.auth.dto

import com.jetbrains.life_science.util.passwordRegex
import javax.validation.constraints.Pattern

data class AuthRequestDTO(

    val email: String,

    val password: String
)
