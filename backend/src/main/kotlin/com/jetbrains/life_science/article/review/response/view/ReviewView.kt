package com.jetbrains.life_science.article.review.response.view

data class ReviewView(
    val id: Long,
    val reviewRequestId: Long,
    val comment: String,
    val reviewerId: Long
)
