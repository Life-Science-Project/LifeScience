package com.jetbrains.life_science.article.review.dto

import com.jetbrains.life_science.article.review.service.ReviewInfo

class ReviewDTOToInfoAdapter(
    val dto: ReviewDTO,
    override val articleVersionId: Long,
    override val reviewerId: Long,
    override val id: Long = 0
) : ReviewInfo {
    override val comment: String
        get() = dto.comment
}
