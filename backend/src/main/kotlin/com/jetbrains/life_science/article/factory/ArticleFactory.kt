package com.jetbrains.life_science.article.factory

import com.jetbrains.life_science.article.entity.Article
import com.jetbrains.life_science.article.service.ArticleInfo
import org.springframework.stereotype.Component

@Component
class ArticleFactory {

    fun create(info: ArticleInfo): Article {
        return Article(info.containerId, info.text, info.tags, info.references)
    }

    fun copy(origin: Article): Article {
        return Article(origin.containerId, origin.text, origin.tags, origin.references)
    }
}
