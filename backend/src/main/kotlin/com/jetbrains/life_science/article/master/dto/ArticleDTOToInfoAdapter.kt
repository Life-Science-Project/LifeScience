package com.jetbrains.life_science.article.master.dto

import com.jetbrains.life_science.article.master.service.ArticleInfo

class ArticleDTOToInfoAdapter(val dto: ArticleDTO) : ArticleInfo {

    override val id: Long
        get() = 0

    override val categoryId: Long
        get() = dto.categoryId
}
