package com.jetbrains.life_science.article.primary.repository

import com.jetbrains.life_science.article.primary.entity.Article
import com.jetbrains.life_science.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByCategories(category: Category): List<Article>
}
