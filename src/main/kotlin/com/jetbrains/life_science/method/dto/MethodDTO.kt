package com.jetbrains.life_science.method.dto

import com.jetbrains.life_science.article.dto.ArticleDTO
import javax.validation.constraints.NotBlank

class MethodDTO(
    @NotBlank
    val name: String,

    val sectionID: Long,

    val articleDTO: ArticleDTO
    )