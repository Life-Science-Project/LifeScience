package com.jetbrains.life_science.user.dto

import javax.validation.constraints.NotBlank

class NewUserDTO(

    @field:NotBlank
    val username: String,

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:NotBlank
    val email: String,

    @field:NotBlank
    val password: String
)