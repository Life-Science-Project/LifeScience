package com.jetbrains.life_science.article.review.view

class ArticleReviewView(
    val id: Long,
    val articleVersionId: Long,
    val comment: String,
    val reviewerId: Long // TODO: add UserView
)
