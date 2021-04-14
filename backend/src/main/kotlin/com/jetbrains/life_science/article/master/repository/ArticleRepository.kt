package com.jetbrains.life_science.article.master.repository

import com.jetbrains.life_science.article.master.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByCategoryId(categoryId: Long): List<Article>
}
