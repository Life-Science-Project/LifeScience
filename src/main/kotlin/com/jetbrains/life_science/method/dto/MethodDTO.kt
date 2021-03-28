package com.jetbrains.life_science.method.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

class MethodDTO(
    @NotBlank
    val name: String,

    @Positive
    val sectionID: Long,
)
