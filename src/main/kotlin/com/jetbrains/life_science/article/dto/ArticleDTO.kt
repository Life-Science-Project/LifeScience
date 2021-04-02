package com.jetbrains.life_science.article.dto

import javax.validation.constraints.NotEmpty

class ArticleDTO(
    @field:NotEmpty
    val text: String
)
