package com.jetbrains.life_science.article.review.request.view

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.response.view.ReviewViewMapper
import com.jetbrains.life_science.article.version.view.ArticleVersionViewMapper
import org.springframework.stereotype.Component

@Component
class ReviewRequestViewMapper(
    val articleVersionViewMapper: ArticleVersionViewMapper,
    val reviewViewMapper: ReviewViewMapper
) {

    fun toView(reviewRequest: ReviewRequest): ReviewRequestView {
        val versionView = articleVersionViewMapper.toView(reviewRequest.version)
        val reviewView = reviewRequest.resolution?.let { reviewViewMapper.toView(it) }
        return ReviewRequestView(
            id = reviewRequest.id,
            destination = reviewRequest.destination,
            version = versionView,
            resolution = reviewView
        )
    }

    fun toViews(reviewRequests: List<ReviewRequest>): List<ReviewRequestView> {
        return reviewRequests.map { toView(it) }
    }
}
