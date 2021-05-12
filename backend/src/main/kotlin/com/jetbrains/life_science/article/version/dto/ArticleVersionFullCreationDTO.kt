package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.master.dto.ArticleDTO
import com.jetbrains.life_science.article.section.dto.SectionInnerDTO
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

data class ArticleVersionFullCreationDTO(

    @Validated
    val articleDTO: ArticleDTO,

    @field:NotBlank
    val name: String,

    val sections: List<SectionInnerDTO> = emptyList()
)
