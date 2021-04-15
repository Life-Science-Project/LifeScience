package com.jetbrains.life_science.article.version.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

// TODO(#58): Add fields to incorporate changes
data class ArticleVersionDTO(

    @field:Positive
    val articleId: Long,

    @field:NotBlank
    val name: String

)
