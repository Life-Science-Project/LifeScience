package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.response.entity.Review

interface PublishApproachRequestService {

    fun get(id: Long): PublishApproachRequest

    fun create(info: PublishApproachRequestInfo): PublishApproachRequest

    fun cancel(id: Long): PublishApproachRequest

    fun addReview(id: Long, review: Review): PublishApproachRequest

    fun delete(id: Long)
}
