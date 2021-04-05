package com.jetbrains.life_science.article.factory

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.entity.ArticleInfo
import org.springframework.stereotype.Component

@Component
class ArticleFactory {

    fun create(info: ArticleInfo): Article {
        val article = Article(info.containerId, info.text, info.references, info.tags)
        return article
    }


}
