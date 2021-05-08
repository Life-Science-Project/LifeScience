package com.jetbrains.life_science.article.review.request.repository

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRequestRepository : JpaRepository<ReviewRequest, Long> {

    fun existsByVersionId(versionId: Long): Boolean

    fun existsByVersionIdAndResolutionIsNull(versionId: Long): Boolean

    fun findAllByVersionId(versionId: Long): List<ReviewRequest>

    fun findAllByVersionAuthorId(authorId: Long): List<ReviewRequest>

    fun findByVersionIdAndResolutionIsNull(versionId: Long): ReviewRequest?
}
