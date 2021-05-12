package com.jetbrains.life_science.article.review.response.factory

import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.review.response.service.ReviewInfo
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.stereotype.Component

@Component
class ReviewFactory {

    fun create(
        info: ReviewInfo
    ): Review {
        return Review(
            id = 0,
            reviewRequest = info.request,
            comment = info.comment,
            reviewer = info.reviewer,
            resolution = info.resolution
        )
    }

    fun setParams(review: Review, info: ReviewInfo, reviewer: User): Review {
        review.comment = info.comment
        review.reviewer = reviewer
        review.resolution = info.resolution
        return review
    }
}
