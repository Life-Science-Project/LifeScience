package com.jetbrains.life_science.article.review.repository

import com.jetbrains.life_science.article.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
    fun findAllByArticleVersionId(versionId: Long): List<Review>
}
