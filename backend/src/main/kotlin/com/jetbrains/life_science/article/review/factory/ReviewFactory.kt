package com.jetbrains.life_science.article.review.factory

import com.jetbrains.life_science.article.review.entity.Review
import com.jetbrains.life_science.article.review.service.ReviewInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.details.entity.User
import org.springframework.stereotype.Component

@Component
class ReviewFactory {
    fun create(info: ReviewInfo, articleVersion: ArticleVersion, user: User): Review {
        return Review(0, articleVersion, info.comment, user)
    }
}
