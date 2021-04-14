package com.jetbrains.life_science.article.review.view

import com.jetbrains.life_science.article.review.entity.ArticleReview
import org.springframework.stereotype.Component

@Component
class ArticleReviewViewMapper {
    fun createReview(articleReview: ArticleReview): ArticleReviewView {
        return ArticleReviewView(
            articleReview.id,
            articleReview.articleVersion.id,
            articleReview.comment,
            articleReview.reviewer.id
        )
    }
}
