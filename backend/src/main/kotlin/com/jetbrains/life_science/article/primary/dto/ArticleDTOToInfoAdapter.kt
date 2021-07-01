package com.jetbrains.life_science.article.primary.dto

import com.jetbrains.life_science.article.primary.service.ArticleInfo

class ArticleDTOToInfoAdapter(
    val dto: ArticleDTO,
    override val id: Long = 0
) : ArticleInfo {

    override val categoryId = dto.categoryId
}
