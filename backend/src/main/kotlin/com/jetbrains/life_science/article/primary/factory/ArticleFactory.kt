package com.jetbrains.life_science.article.primary.factory

import com.jetbrains.life_science.article.primary.entity.Article
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class ArticleFactory {

    fun create(category: Category): Article {
        return Article(
            id = 0,
            category = category,
            versions = mutableListOf()
        )
    }

    fun setParams(article: Article, category: Category) {
        article.category = category
    }
}
