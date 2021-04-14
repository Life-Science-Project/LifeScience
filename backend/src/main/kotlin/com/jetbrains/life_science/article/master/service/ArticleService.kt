package com.jetbrains.life_science.article.master.service

import com.jetbrains.life_science.article.master.entity.Article

interface ArticleService {

    fun create(info: ArticleInfo)

    fun getById(id: Long): Article

    fun getByCategoryId(categoryId: Long): List<Article>
}
