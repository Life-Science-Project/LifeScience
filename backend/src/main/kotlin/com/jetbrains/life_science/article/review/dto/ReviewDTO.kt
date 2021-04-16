package com.jetbrains.life_science.article.review.dto

import javax.validation.constraints.Positive

class ReviewDTO(

    @field:Positive
    val articleVersionId: Long,

    val comment: String,

    @field:Positive
    val reviewerId: Long
)
