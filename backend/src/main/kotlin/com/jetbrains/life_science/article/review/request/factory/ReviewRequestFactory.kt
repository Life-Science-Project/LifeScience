package com.jetbrains.life_science.article.review.request.factory

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class ReviewRequestFactory {

    fun create(version: ArticleVersion, destination: VersionDestination): ReviewRequest {
        return ReviewRequest(
            id = 0,
            version = version,
            destination = destination,
            resolution = null
        )
    }
}
