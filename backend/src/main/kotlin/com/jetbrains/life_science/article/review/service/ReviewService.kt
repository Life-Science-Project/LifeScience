package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.Review
import com.jetbrains.life_science.user.entity.User

interface ReviewService {

    fun addReview(info: ReviewInfo): Review

    fun deleteReview(reviewId: Long)

    fun getAllByVersionId(articleVersionId: Long, user: User): List<Review>

    fun getById(reviewId: Long, user: User): Review

    fun updateById(reviewId: Long, reviewInfo: ReviewInfo, user: User): Review
}
