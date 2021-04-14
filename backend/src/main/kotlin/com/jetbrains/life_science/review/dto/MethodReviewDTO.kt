package com.jetbrains.life_science.review.dto

import javax.validation.constraints.Positive

class MethodReviewDTO(

    @field:Positive
    val methodId: Long,

    val comment: String,

    @field:Positive
    val authorId: Long
)
