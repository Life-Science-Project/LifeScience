package com.jetbrains.life_science.controller.auth.view

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class AuthRequestDTO(

    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)