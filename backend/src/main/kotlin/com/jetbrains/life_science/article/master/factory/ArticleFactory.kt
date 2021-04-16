package com.jetbrains.life_science.article.master.factory

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.master.service.ArticleInfo
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class ArticleFactory {

    fun create(category: Category): Article {
        return Article(0, category)
    }

    fun setParams(article: Article, info: ArticleInfo, category: Category) {
        article.category = category
    }
}
