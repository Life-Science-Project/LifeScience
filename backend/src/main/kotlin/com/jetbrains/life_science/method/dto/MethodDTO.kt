package com.jetbrains.life_science.method.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

class MethodDTO(
    @field:NotBlank
    val name: String,

    @field:Positive
    val sectionID: Long,
)
