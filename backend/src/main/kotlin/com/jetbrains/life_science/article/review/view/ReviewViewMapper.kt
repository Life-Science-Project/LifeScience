package com.jetbrains.life_science.article.review.view

import com.jetbrains.life_science.article.review.entity.Review
import org.springframework.stereotype.Component

@Component
class ReviewViewMapper {
    fun createView(review: Review): ReviewView {
        return ReviewView(
            review.id,
            review.articleVersion.id,
            review.comment,
            review.reviewer.id
        )
    }
}
