package com.jetbrains.life_science.article.paragraph.dto

import javax.validation.constraints.Positive

class ParagraphDTO(

    @field:Positive
    val sectionId: Long,

    val text: String,

    val references: MutableList<String>,

    val tags: MutableList<String>
)
