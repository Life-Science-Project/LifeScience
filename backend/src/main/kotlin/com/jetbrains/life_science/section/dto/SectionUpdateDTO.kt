package com.jetbrains.life_science.section.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class SectionUpdateDTO(

    @field:Positive
    val id: Long,

    @field:NotBlank
    val name: String,

    val description: String

)
