package com.jetbrains.life_science.user.master.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class LoginDTO(

    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
