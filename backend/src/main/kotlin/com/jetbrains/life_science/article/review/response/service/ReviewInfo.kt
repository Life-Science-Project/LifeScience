package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.user.master.entity.User

interface ReviewInfo {
    val request: ReviewRequest

    val comment: String

    val reviewer: User

    val resolution: ReviewResolution
}
