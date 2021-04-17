package com.jetbrains.life_science.user.credentials.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class NewUserDTO(

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
