package com.jetbrains.life_science.article.primary.factory

import com.jetbrains.life_science.article.primary.entity.Article
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class ArticleFactory {

    fun create(categories: MutableList<Category>): Article {
        return Article(
            id = 0,
            categories = categories,
            versions = mutableListOf()
        )
    }

    fun setParams(article: Article, categories: MutableList<Category>) {
        article.categories = categories
    }
}
