package com.jetbrains.life_science.article.dto

import com.jetbrains.life_science.article.entity.ArticleInfo

class ArticleDTOToInfoAdapter(private val articleDTO: ArticleDTO) : ArticleInfo {
    override fun getId(): Long {
        return 0
    }

    override fun getText(): String {
        return articleDTO.text
    }
}
