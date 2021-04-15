package com.jetbrains.life_science.article.version.repository

import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleVersionRepository : JpaRepository<ArticleVersion, Long> {

    fun findByMainArticleIdAndState(id: Long, state: State): ArticleVersion?

    fun findAllByMainArticleId(id: Long): List<ArticleVersion>
}
