package com.jetbrains.life_science.review.request.service.publish

import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.service.JudgeExecutorService
import com.jetbrains.life_science.review.response.entity.Review

interface PublishApproachRequestService : JudgeExecutorService {

    fun get(id: Long): PublishApproachRequest

    fun create(info: PublishApproachRequestInfo): PublishApproachRequest

    override fun cancel(id: Long): PublishApproachRequest

    fun addReview(id: Long, review: Review): PublishApproachRequest

    override fun delete(id: Long)
}
