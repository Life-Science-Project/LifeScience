package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.ArticleReview
import com.jetbrains.life_science.user.entity.User

interface ArticleReviewService {

    fun addReview(info: ArticleReviewInfo): ArticleReview

    fun deleteReview(reviewId: Long)

    fun getAllByVersionId(articleVersionId: Long, user: User): List<ArticleReview>

    fun getById(reviewId: Long, user: User): ArticleReview

    fun updateById(reviewId: Long, reviewInfo: ArticleReviewInfo, user: User): ArticleReview
}
