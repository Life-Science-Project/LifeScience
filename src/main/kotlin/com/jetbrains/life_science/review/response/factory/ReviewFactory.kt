package com.jetbrains.life_science.review.response.factory

import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.service.ReviewInfo
import org.springframework.stereotype.Component

@Component
class ReviewFactory {
    fun createReview(info: ReviewInfo): Review {
        return Review(
            id = info.id,
            date = info.date,
            comment = info.comment,
            resolution = info.resolution,
            reviewer = info.reviewer
        )
    }
}
