package com.jetbrains.life_science.article.view

import com.jetbrains.life_science.article.entity.Article
import org.springframework.stereotype.Component

@Component
class ArticleViewMapper {
    fun createView(article: Article): ArticleView {
        return ArticleView()
    }
}