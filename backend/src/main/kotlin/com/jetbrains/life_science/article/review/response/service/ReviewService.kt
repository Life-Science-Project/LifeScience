package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User

interface ReviewService {

    fun getByVersionId(versionId: Long): Review?

    fun getById(reviewId: Long): Review

    fun addReview(info: ReviewInfo): Review

    fun deleteReview(id: Long)

    fun getAllByVersion(version: ArticleVersion): List<Review>

    fun getAllByVersionAndUser(version: ArticleVersion, user: User): List<Review>

    fun getByRequest(request: ReviewRequest): Review?
}
