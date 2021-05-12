package com.jetbrains.life_science.article.review.request.view

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.response.view.ReviewView
import com.jetbrains.life_science.article.version.view.ArticleVersionView

data class ReviewRequestView(
    val id: Long,
    val destination: VersionDestination,
    val version: ArticleVersionView,
    val resolution: ReviewView?
)
