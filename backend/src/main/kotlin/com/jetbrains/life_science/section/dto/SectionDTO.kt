package com.jetbrains.life_science.section.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class SectionDTO(

    @field:NotBlank
    val name: String,

    @field:Positive
    val articleVersionId: Long,

    val description: String = ""
)
