package com.jetbrains.life_science.article.factory

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo
import org.springframework.stereotype.Component

@Component
class ArticleFactory {
    fun createArticle(articleInfo: ArticleInfo) : Article {
        return Article(articleInfo.getId())
    }
}