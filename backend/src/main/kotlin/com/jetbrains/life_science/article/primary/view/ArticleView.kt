package com.jetbrains.life_science.article.primary.view

import com.jetbrains.life_science.article.version.view.ArticleVersionView

data class ArticleView(
    val id: Long,
    val version: ArticleVersionView?,
    val protocols: List<ArticleVersionView>
)
