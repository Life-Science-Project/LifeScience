package com.jetbrains.life_science.article.repository

import com.jetbrains.life_science.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long>