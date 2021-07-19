package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.response.entity.Review

interface PublishProtocolRequestService {

    fun get(id: Long): PublishProtocolRequest

    fun create(info: PublishProtocolRequestInfo): PublishProtocolRequest

    fun cancel(id: Long): PublishProtocolRequest

    fun addReview(id: Long, review: Review): PublishProtocolRequest

    fun delete(id: Long)
}
