package com.jetbrains.life_science.article.review.response.factory

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.service.ReviewInfo
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.stereotype.Component

@Component
class ReviewFactory {

    fun create(
        resolution: ReviewResolution,
        comment: String,
        request: ReviewRequest,
        user: User
    ): Review {
        return Review(
            id = 0,
            reviewRequest = request,
            comment = comment,
            reviewer = user,
            resolution = resolution
        )
    }

    fun setParams(review: Review, info: ReviewInfo, reviewer: User): Review {
        review.comment = info.comment
        review.reviewer = reviewer
        review.resolution = info.resolution
        return review
    }
}
