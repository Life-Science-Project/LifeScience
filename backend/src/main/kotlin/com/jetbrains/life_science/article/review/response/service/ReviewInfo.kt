package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User

interface ReviewInfo {
    val version: ArticleVersion

    val comment: String

    val reviewer: User

    val resolution: ReviewResolution
}
