package com.jetbrains.life_science.article.review.repository

import com.jetbrains.life_science.article.review.entity.ArticleReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleReviewRepository : JpaRepository<ArticleReview, Long>
