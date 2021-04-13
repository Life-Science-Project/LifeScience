package com.jetbrains.life_science.method.dto

import javax.validation.constraints.Positive

data class MethodDTO(
    @field:Positive
    val sectionId: Long,
)
