package com.jetbrains.life_science.article.repository

import com.jetbrains.life_science.article.entity.Article

interface ArticleSearch {
    fun search(text: String): List<Article>
}
