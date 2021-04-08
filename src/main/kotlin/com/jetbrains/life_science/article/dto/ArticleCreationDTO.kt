package com.jetbrains.life_science.article.dto

import javax.validation.constraints.Positive

class ArticleCreationDTO(

    @field:Positive
    val containerId: Long,

    val text: String,

    val references: MutableList<String>,

    val tags: MutableList<String>
)
