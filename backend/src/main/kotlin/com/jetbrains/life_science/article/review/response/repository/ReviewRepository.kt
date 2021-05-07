package com.jetbrains.life_science.article.review.response.repository

import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    fun findAllByReviewRequestVersionId(versionId: Long): List<Review>

    fun findByReviewRequestVersionId(versionId: Long): Review?

    fun findAllByReviewRequestVersionIdAndReviewRequestVersionAuthor(versionId: Long, author: User): List<Review>
}
