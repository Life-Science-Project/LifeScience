package com.jetbrains.life_science.article.section.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class SectionUpdateDTO(

    @field:NotBlank
    val name: String,

    val description: String

)
