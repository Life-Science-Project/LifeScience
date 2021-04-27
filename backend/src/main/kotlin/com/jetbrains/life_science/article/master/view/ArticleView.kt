package com.jetbrains.life_science.article.master.view

import com.jetbrains.life_science.article.version.view.ArticleVersionView

data class ArticleView(
    val id: Long,
    val version: ArticleVersionView?
)
