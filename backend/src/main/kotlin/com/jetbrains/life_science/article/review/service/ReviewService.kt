package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.Review
import com.jetbrains.life_science.user.credentials.entity.UserCredentials

interface ReviewService {

    fun addReview(info: ReviewInfo): Review

    fun deleteReview(reviewId: Long)

    fun getAllByVersionId(articleVersionId: Long, user: UserCredentials): List<Review>

    fun getById(reviewId: Long, user: UserCredentials): Review

    fun updateById(reviewInfo: ReviewInfo, user: UserCredentials): Review
}
