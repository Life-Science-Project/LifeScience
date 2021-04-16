package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.Review

interface ReviewService {

    fun addReview(info: ReviewInfo): Review

    fun deleteReview(reviewId: Long)

    fun getAllByVersionId(articleVersionId: Long): List<Review>

    fun getById(reviewId: Long): Review
}
