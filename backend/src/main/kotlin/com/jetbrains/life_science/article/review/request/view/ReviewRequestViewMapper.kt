package com.jetbrains.life_science.article.review.request.view

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import org.springframework.stereotype.Component

@Component
class ReviewRequestViewMapper {

    fun toView(reviewRequest: ReviewRequest): ReviewRequestView {
        return ReviewRequestView()
    }

    fun toViews(reviewRequests: List<ReviewRequest>): List<ReviewRequestView> {
        return reviewRequests.map { toView(it) }
    }

}
