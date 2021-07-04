package com.jetbrains.life_science.article.primary.service

import com.jetbrains.life_science.article.primary.entity.Article

interface ArticleService {

    fun create(info: ArticleInfo): Article

    fun getById(id: Long): Article

    fun update(info: ArticleInfo): Article

    fun deleteById(articleId: Long)

    fun countAll(): Long
}
