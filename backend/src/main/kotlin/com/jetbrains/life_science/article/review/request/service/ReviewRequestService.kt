package com.jetbrains.life_science.article.review.request.service

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest

/**
 * @author Потапов Александр
 * @since 06.05.2021
 */
interface ReviewRequestService {

    fun addRequest(info: ReviewRequestInfo): ReviewRequest

    fun removeRequest(reviewId: Long)

    fun getById(reviewRequestId: Long): ReviewRequest

    fun getRequestsByVersionId(versionId: Long): List<ReviewRequest>

    fun getRequestsByAuthorId(authorId: Long): List<ReviewRequest>

    fun getByVersionId(versionId: Long): ReviewRequest?

    fun getByVersionIdOrThrow(versionId: Long): ReviewRequest
}
