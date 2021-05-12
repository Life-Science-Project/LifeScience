package com.jetbrains.life_science.article.review.request.factory

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.request.service.ReviewRequestInfo
import org.springframework.stereotype.Component

@Component
class ReviewRequestFactory {

    fun create(info: ReviewRequestInfo): ReviewRequest {
        return ReviewRequest(
            id = 0,
            version = info.version,
            destination = info.destination,
            resolution = null
        )
    }
}
