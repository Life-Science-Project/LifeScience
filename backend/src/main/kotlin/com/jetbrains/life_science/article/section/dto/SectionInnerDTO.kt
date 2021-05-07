package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.content.publish.dto.ContentInnerDTO
import com.jetbrains.life_science.article.section.parameter.dto.ParameterDTO
import javax.validation.constraints.NotBlank

data class SectionInnerDTO(
    @field:NotBlank
    val name: String,

    val description: String = "",

    val parameters: List<ParameterDTO> = emptyList(),

    var visible: Boolean = true,

    val content: ContentInnerDTO? = null
)
