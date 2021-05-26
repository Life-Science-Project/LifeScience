package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.user.master.entity.User

interface ArticleVersionService {

    fun changeState(version: ArticleVersion, state: State): ArticleVersion

    fun approve(version: ArticleVersion, destination: VersionDestination)

    fun checkExistenceById(versionId: Long)

    fun create(info: ArticleVersionCreationInfo): ArticleVersion

    fun archive(versionId: Long)

    fun createCopy(versionId: Long, user: User): ArticleVersion

    fun getPublished(versionId: Long): ArticleVersion

    fun getById(id: Long): ArticleVersion

    fun getByArticleId(articleId: Long): List<ArticleVersion>

    fun updateById(info: ArticleVersionInfo): ArticleVersion

    fun getPublishedByArticle(mainArticle: Article): ArticleVersion

}
