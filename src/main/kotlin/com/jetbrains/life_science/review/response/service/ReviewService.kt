package com.jetbrains.life_science.review.response.service

import com.jetbrains.life_science.review.response.entity.Review

interface ReviewService {
    fun getReview(reviewId: Long): Review
    fun createReview(info: ReviewInfo): Review
    fun deleteReview(id: Long)
}
