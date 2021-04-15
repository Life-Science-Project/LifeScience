package com.jetbrains.life_science.article.review.service

interface ReviewInfo {
    val articleVersionId: Long

    val comment: String

    val reviewerId: Long
}
