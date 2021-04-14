package com.jetbrains.life_science.article.content.dto

import javax.validation.constraints.Positive

class ContentDTO(

    @field:Positive
    val sectionId: Long,

    val text: String,

    val references: MutableList<String>,

    val tags: MutableList<String>
)
