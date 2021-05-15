package com.jetbrains.life_science.article.review.request.repository

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRequestRepository : JpaRepository<ReviewRequest, Long> {

    fun existsByVersionAndResolutionIsNull(version: ArticleVersion): Boolean

    fun findAllByVersionIdAndResolutionIsNull(versionId: Long): List<ReviewRequest>

    fun findByVersionIdAndResolutionIsNull(versionId: Long): ReviewRequest?

    fun findAllByVersionId(versionId: Long): List<ReviewRequest>
}
