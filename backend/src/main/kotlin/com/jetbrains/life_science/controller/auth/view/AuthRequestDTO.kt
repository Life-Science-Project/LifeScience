package com.jetbrains.life_science.controller.auth.view

import javax.validation.constraints.NotBlank

data class AuthRequestDTO(

    val email: String,

    @field:NotBlank
    val password: String
)
