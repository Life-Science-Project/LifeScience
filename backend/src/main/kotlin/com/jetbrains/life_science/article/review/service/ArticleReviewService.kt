package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.ArticleReview

interface ArticleReviewService {

    fun addReview(info: ArticleReviewInfo): ArticleReview

    fun deleteReview(reviewId: Long)

    fun getAllByVersionId(articleVersionId: Long): List<ArticleReview>

    fun getById(reviewId: Long): ArticleReview
}
