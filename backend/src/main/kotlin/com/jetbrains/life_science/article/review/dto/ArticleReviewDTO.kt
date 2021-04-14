package com.jetbrains.life_science.article.review.dto

import javax.validation.constraints.Positive

class ArticleReviewDTO(

    @field:Positive
    val articleVersionId: Long,

    val comment: String,

    @field:Positive
    val authorId: Long
)
