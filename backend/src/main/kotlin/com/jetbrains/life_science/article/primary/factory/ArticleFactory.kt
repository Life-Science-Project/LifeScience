package com.jetbrains.life_science.article.primary.factory

import com.jetbrains.life_science.article.primary.entity.Article
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class ArticleFactory {

    fun create(categories: List<Category>): Article {
        return Article(
            id = 0,
            categories = categories.toMutableList(),
            versions = mutableListOf()
        )
    }

    fun setParams(article: Article, categories: List<Category>) {
        article.categories = categories.toMutableList()
    }
}
