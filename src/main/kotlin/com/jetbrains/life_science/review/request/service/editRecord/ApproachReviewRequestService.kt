package com.jetbrains.life_science.review.request.service.editRecord

import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.review.request.service.JudgeExecutorService
import com.jetbrains.life_science.review.response.entity.Review

interface ApproachReviewRequestService : JudgeExecutorService {

    fun get(id: Long): ApproachReviewRequest

    fun create(info: ApproachReviewRequestInfo): ApproachReviewRequest

    override fun cancel(id: Long): ApproachReviewRequest

    fun addReview(id: Long, review: Review): ApproachReviewRequest

    override fun delete(id: Long)
}
