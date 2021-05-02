package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User

interface ArticleVersionService {

    fun createBlank(info: ArticleVersionCreationInfo): ArticleVersion

    fun approve(versionId: Long)

    fun archive(versionId: Long)

    fun createCopy(articleId: Long, user: User): ArticleVersion

    fun getPublishedVersion(articleId: Long): ArticleVersion

    fun getById(id: Long): ArticleVersion

    fun getByArticleId(articleId: Long): List<ArticleVersion>

    fun getByArticleIdAndUser(articleId: Long, user: User): List<ArticleVersion>

    fun updateById(info: ArticleVersionInfo): ArticleVersion
}
