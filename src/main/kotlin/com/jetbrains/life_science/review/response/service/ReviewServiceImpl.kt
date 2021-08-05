package com.jetbrains.life_science.review.response.service

import com.jetbrains.life_science.exception.not_found.ReviewNotFoundException
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.factory.ReviewFactory
import com.jetbrains.life_science.review.response.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(
    val repository: ReviewRepository,
    val factory: ReviewFactory,
) : ReviewService {
    override fun getReview(reviewId: Long): Review {
        return repository.findById(reviewId).orElseThrow {
            ReviewNotFoundException("Review with id $reviewId is not found")
        }
    }

    override fun createReview(info: ReviewInfo): Review {
        val review = factory.createReview(info)
        return repository.save(review)
    }

    override fun deleteReview(id: Long) {
        val review = getReview(id)
        repository.deleteById(review.id)
    }
}
