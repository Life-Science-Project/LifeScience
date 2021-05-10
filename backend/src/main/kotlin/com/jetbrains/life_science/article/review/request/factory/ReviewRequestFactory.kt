package com.jetbrains.life_science.article.review.request.factory

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion
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
