package com.jetbrains.life_science.article.version.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class ArticleVersionDTO(

    @field:Positive
    val articleId: Long,

    @field:NotBlank
    val name: String

)
