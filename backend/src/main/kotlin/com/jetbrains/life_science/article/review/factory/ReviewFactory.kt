package com.jetbrains.life_science.article.review.factory

import com.jetbrains.life_science.article.review.entity.Review
import com.jetbrains.life_science.article.review.service.ReviewInfo
import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class ReviewFactory {
    fun create(info: ReviewInfo, articleVersion: ArticleVersion, userCredentials: UserCredentials): Review {
        return Review(0, articleVersion, info.comment, userCredentials)
    }
}
