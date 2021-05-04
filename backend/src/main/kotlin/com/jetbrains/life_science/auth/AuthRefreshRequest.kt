package com.jetbrains.life_science.auth

import javax.validation.constraints.NotBlank

class AuthRefreshRequest(

    @field:NotBlank
    val jwt: String,

    @field:NotBlank
    val refreshToken: String
)
