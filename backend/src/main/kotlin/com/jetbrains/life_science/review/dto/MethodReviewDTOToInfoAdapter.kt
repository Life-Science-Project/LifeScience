package com.jetbrains.life_science.review.dto

import com.jetbrains.life_science.review.service.MethodReviewInfo

class MethodReviewDTOToInfoAdapter(
    val dto: MethodReviewDTO
) : MethodReviewInfo {
    override val methodId: Long
        get() = dto.methodId
    override val comment: String
        get() = dto.comment
    override val authorId: Long
        get() = dto.authorId
}
