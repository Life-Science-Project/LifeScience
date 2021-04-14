package com.jetbrains.life_science.article.review.service

interface ArticleReviewInfo {
    val articleVersionId: Long

    val comment: String

    val authorId: Long
}
