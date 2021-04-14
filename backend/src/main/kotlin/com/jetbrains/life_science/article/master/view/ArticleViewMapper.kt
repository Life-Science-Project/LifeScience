package com.jetbrains.life_science.article.master.view

import com.jetbrains.life_science.article.master.entity.Article
import org.springframework.stereotype.Component

@Component
class ArticleViewMapper {
    fun createView(article: Article): ArticleView {
        return ArticleView(article.id)
    }
}
