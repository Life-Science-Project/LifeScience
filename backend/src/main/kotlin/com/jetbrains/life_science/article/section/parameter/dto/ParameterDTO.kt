package com.jetbrains.life_science.article.section.parameter.dto

import javax.validation.constraints.NotBlank

data class ParameterDTO(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val defaultValue: String
)
