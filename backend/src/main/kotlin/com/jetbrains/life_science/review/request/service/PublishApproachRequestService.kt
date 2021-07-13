package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest

interface PublishApproachRequestService {

    fun get(id: Long): PublishApproachRequest

    fun create(info: PublishApproachRequestInfo): PublishApproachRequest

    fun approve(id: Long): PublishApproachRequest

    fun cancel(id: Long): PublishApproachRequest

    fun addReview(id: Long, review: Review): PublishApproachRequest
}
