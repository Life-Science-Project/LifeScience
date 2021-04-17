package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.version.entity.ArticleVersion

interface ArticleVersionService {

    fun createBlank(info: ArticleVersionInfo): ArticleVersion

    fun approve(id: Long)

    fun createCopy(articleId: Long)

    fun getPublishedVersion(articleId: Long): ArticleVersion

    fun getById(id: Long): ArticleVersion

    fun getByArticleId(articleId: Long): List<ArticleVersion>

    fun updateById(info: ArticleVersionInfo): ArticleVersion
}
