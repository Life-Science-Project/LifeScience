package com.jetbrains.life_science.controller.review.response.view

import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.review.response.entity.Review
import org.springframework.stereotype.Component

@Component
class ReviewViewMapper(
    val userViewMapper: UserViewMapper
) {
    fun toView(review: Review): ReviewView {
        val userView = review.reviewer.userPersonalData?.let {
            userViewMapper.toShortView(it)
        }
        return ReviewView(
            id = review.id,
            comment = review.comment,
            resolution = review.resolution,
            date = review.date,
            reviewer = userView
        )
    }

    fun toViewsAll(reviews: List<Review>): List<ReviewView> {
        return reviews.map { toView(it) }
    }
}
