package com.jetbrains.life_science.article.master.dto

import com.jetbrains.life_science.article.master.service.ArticleInfo

class ArticleDTOToInfoAdapter(val dto: ArticleDTO) : ArticleInfo {
    override val categoryId: Long
        get() = dto.categoryId
}
