package com.jetbrains.life_science.user.dto

import javax.validation.constraints.NotBlank

class LoginDTO(

    @NotBlank
    var username: String,

    @NotBlank
    var password: String
)