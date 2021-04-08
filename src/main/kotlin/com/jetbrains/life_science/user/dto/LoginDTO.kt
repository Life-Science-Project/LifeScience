package com.jetbrains.life_science.user.dto

import javax.validation.constraints.NotBlank

class LoginDTO(
    
    @field:NotBlank
    val username: String,
    
    @field:NotBlank
    val password: String
)
