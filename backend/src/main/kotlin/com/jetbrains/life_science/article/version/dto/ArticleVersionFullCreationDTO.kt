package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.primary.dto.ArticleDTO
import com.jetbrains.life_science.article.section.dto.SectionInnerDTO
import org.springframework.validation.annotation.Validated

class ArticleVersionFullCreationDTO(

    @Validated
    val articleDTO: ArticleDTO,

    name: String,

    sections: List<SectionInnerDTO> = emptyList()

) : ArticleVersionCreationDTO(name, sections)
