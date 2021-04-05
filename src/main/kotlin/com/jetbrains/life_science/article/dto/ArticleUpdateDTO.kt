package com.jetbrains.life_science.article.dto

import javax.validation.constraints.NotBlank

data class ArticleUpdateDTO(

    @NotBlank
    val id: String,

    val text: String
)
