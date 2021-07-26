package com.jetbrains.life_science.review.request.service.publish

import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.service.JudgeExecutorService
import com.jetbrains.life_science.review.response.entity.Review

interface PublishProtocolRequestService : JudgeExecutorService {

    fun get(id: Long): PublishProtocolRequest

    fun create(info: PublishProtocolRequestInfo): PublishProtocolRequest

    override fun cancel(id: Long): PublishProtocolRequest

    fun addReview(id: Long, review: Review): PublishProtocolRequest

    override fun delete(id: Long)
}
