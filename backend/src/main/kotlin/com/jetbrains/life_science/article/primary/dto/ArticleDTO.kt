package com.jetbrains.life_science.article.primary.dto

import javax.validation.constraints.Positive

data class ArticleDTO(
    @field:Positive
    val categoryId: Long,
)
