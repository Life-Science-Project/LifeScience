package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.user.master.entity.User

interface ReviewService {

    fun approve(versionId: Long, reviewer: User, destination: VersionDestination)

    fun requestChanges(info: ReviewInfo)

    fun getAllByVersionId(versionId: Long, user: User): List<Review>

    fun getByVersionId(versionId: Long): Review?

    fun getById(reviewId: Long): Review

    fun addReview(info: ReviewInfo): Review

    fun update(info: ReviewInfo): Review

    fun deleteReview(id: Long)
}
