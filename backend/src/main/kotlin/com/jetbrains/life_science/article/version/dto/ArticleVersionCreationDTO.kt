package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.master.dto.ArticleDTO
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class ArticleVersionCreationDTO(

    @Validated
    val articleDTO: ArticleDTO,

    @field:NotBlank
    val name: String

)
