package com.jetbrains.life_science.article.version.repository

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleVersionRepository : JpaRepository<ArticleVersion, Long> {

    fun findByMainArticleIdAndStateIn(id: Long, states: List<State>): ArticleVersion?

    fun findByIdAndStateIn(id: Long, states: List<State>): ArticleVersion?

    fun findByMainArticleIdAndState(id: Long, state: State): ArticleVersion?

    fun findAllByMainArticleIdAndState(id: Long, state: State): List<ArticleVersion>

    fun findAllByMainArticleId(id: Long): List<ArticleVersion>

    fun findAllByMainArticleIdAndAuthor(id: Long, author: User): List<ArticleVersion>
}
