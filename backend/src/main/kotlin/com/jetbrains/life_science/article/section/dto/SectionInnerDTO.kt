package com.jetbrains.life_science.article.section.dto

import com.jetbrains.life_science.article.content.publish.dto.ContentInnerDTO
import javax.validation.constraints.NotBlank

data class SectionInnerDTO(
    @field:NotBlank
    val name: String,

    val description: String = "",


    var visible: Boolean = true,

    val content: ContentInnerDTO? = null
)
