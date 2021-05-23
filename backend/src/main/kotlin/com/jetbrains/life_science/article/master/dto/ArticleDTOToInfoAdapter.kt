package com.jetbrains.life_science.article.master.dto

import com.jetbrains.life_science.article.master.service.ArticleInfo

class ArticleDTOToInfoAdapter(
    val dto: ArticleDTO,
    override val id: Long = 0
) : ArticleInfo {

    override val categoryId = dto.categoryId
}
