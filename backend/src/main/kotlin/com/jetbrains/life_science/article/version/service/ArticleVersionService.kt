package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User

interface ArticleVersionService {

    fun createBlank(info: ArticleVersionCreationInfo): ArticleVersion

    fun approveGlobal(versionId: Long)

    fun approveUserLocal(versionId: Long)

    fun archive(versionId: Long)

    fun createCopy(versionId: Long, user: User): ArticleVersion

    fun getPublishedVersion(versionId: Long): ArticleVersion

    fun getById(id: Long): ArticleVersion

    fun getByArticleId(articleId: Long): List<ArticleVersion>

    fun updateById(info: ArticleVersionInfo): ArticleVersion

    fun moveToEdit(articleVersion: ArticleVersion)

    fun getUserPublishedVersions(articleId: Long): List<ArticleVersion>
}
