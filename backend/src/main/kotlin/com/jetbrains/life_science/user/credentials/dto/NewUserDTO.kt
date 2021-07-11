package com.jetbrains.life_science.user.credentials.dto

import javax.validation.constraints.NotBlank

data class NewUserDTO(

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    val email: String,

    @field:NotBlank
    val password: String
)
