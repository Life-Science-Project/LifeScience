package com.jetbrains.life_science.article.review.response.repository

import com.jetbrains.life_science.article.review.response.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    fun findAllByReviewRequestVersionId(versionId: Long): List<Review>

    fun findByReviewRequestVersionId(versionId: Long): Review?

    fun findAllByReviewRequestVersionIdAndReviewRequestVersionAuthorId(versionId: Long, authorId: Long): List<Review>
}
