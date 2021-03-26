package com.jetbrains.life_science.article.view

import com.jetbrains.life_science.article.entity.Article

class ArticleViewMapper {
    companion object {
        fun createView(article: Article) : ArticleView {
            return ArticleView()
        }
    }
}