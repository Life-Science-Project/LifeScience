package com.jetbrains.life_science.user.master.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class NewUserDTO(

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
