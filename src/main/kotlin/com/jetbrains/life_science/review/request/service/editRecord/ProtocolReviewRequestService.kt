package com.jetbrains.life_science.review.request.service.editRecord

import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.review.request.service.JudgeExecutorService
import com.jetbrains.life_science.review.response.entity.Review

interface ProtocolReviewRequestService : JudgeExecutorService {

    fun get(id: Long): ProtocolReviewRequest

    fun create(info: ProtocolReviewRequestInfo): ProtocolReviewRequest

    override fun cancel(id: Long): ProtocolReviewRequest

    fun addReview(id: Long, review: Review): ProtocolReviewRequest

    override fun delete(id: Long)
}
