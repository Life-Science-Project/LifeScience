package com.jetbrains.life_science.user.dto

import javax.validation.constraints.NotBlank

class NewUserDTO(

    @NotBlank
    var username: String,

    @NotBlank
    var firstName: String,

    @NotBlank
    var lastName: String,

    @NotBlank
    var email: String,

    @NotBlank
    var password: String
)