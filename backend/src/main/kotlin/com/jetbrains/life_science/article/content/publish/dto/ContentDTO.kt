package com.jetbrains.life_science.article.content.publish.dto

import javax.validation.constraints.Positive

data class ContentDTO(

    @field:Positive
    val sectionId: Long,

    val text: String,

    val references: List<String> = emptyList(),

    val tags: List<String> = emptyList()
)
