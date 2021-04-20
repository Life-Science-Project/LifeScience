package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.section.parameter.dto.ParameterDTO
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class SectionDTO(

    @field:NotBlank
    val name: String,

    @field:Positive
    val articleVersionId: Long,

    val description: String = "",

    val parameters: List<ParameterDTO>,

    val order: Int
)
