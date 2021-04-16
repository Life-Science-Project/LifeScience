package com.jetbrains.life_science.article.master.factory

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class ArticleFactory {

    fun create(category: Category, articleId: Long = 0): Article {
        return Article(articleId, category)
    }
}
