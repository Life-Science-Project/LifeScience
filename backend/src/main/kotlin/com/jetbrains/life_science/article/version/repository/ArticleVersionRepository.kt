package com.jetbrains.life_science.article.version.repository

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.user.details.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleVersionRepository : JpaRepository<ArticleVersion, Long> {

    fun findByMainArticleIdAndState(id: Long, state: State): ArticleVersion?

    fun findAllByMainArticleId(id: Long): List<ArticleVersion>

    fun findAllByMainArticleIdAndAuthor(id: Long, author: User): List<ArticleVersion>

}
