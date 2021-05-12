package com.jetbrains.life_science.article.review.response.repository

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {

    fun findByReviewRequestVersionId(versionId: Long): Review?

    fun findAllByReviewRequestVersionAndReviewRequestVersionAuthor(version: ArticleVersion, author: User): List<Review>

    fun findAllByReviewRequestVersion(version: ArticleVersion): List<Review>

    fun findByReviewRequest(reviewRequest: ReviewRequest): Review?
}
