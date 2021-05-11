package com.jetbrains.life_science.article.review.request.service

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.version.entity.ArticleVersion

interface ReviewRequestService {

    fun add(info: ReviewRequestInfo): ReviewRequest

    fun removeRequest(reviewId: Long)

    fun getById(reviewRequestId: Long): ReviewRequest

    fun getAllByVersion(versionId: ArticleVersion): List<ReviewRequest>

    fun getByVersionId(versionId: Long): ReviewRequest?

    fun getByVersionIdOrThrow(versionId: Long): ReviewRequest

    fun getAllActiveByVersionId(versionId: Long): List<ReviewRequest>

    fun delete(request: ReviewRequest)
}
