package com.jetbrains.life_science.article.section.dto

import javax.validation.constraints.NotBlank

data class SectionUpdateDTO(

    @field:NotBlank
    val name: String,

    val description: String

)
