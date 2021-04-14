package com.jetbrains.life_science.review.service

interface MethodReviewInfo {
    val methodId: Long

    val comment: String

    val authorId: Long
}
