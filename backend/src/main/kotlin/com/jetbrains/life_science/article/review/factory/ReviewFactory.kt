package com.jetbrains.life_science.article.review.factory

import com.jetbrains.life_science.article.review.entity.Review
import com.jetbrains.life_science.article.review.service.ReviewInfo
import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class ReviewFactory {
    fun create(info: ReviewInfo, articleVersion: ArticleVersion, user: User): Review {
        return Review(info.id, articleVersion, info.comment, user)
    }

    fun setParams(articleReview: Review, info: ReviewInfo, version: ArticleVersion, reviewer: User) {
        articleReview.comment = info.comment
        articleReview.articleVersion = version
        articleReview.reviewer = reviewer
    }
}
