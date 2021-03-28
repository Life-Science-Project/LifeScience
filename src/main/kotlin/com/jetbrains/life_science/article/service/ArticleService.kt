package com.jetbrains.life_science.article.service

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo

interface ArticleService {
    fun addArticle(articleInfo: ArticleInfo): Article

    fun deleteArticle(id: Long)

    fun getArticle(id: Long): Article
}
