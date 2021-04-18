package com.jetbrains.life_science.article.review.dto

import com.jetbrains.life_science.article.review.service.ReviewInfo

class ReviewDTOToInfoAdapter(
    val dto: ReviewDTO,
    override val id: Long = 0
) : ReviewInfo {
    override val articleVersionId: Long
        get() = dto.articleVersionId
    override val comment: String
        get() = dto.comment
    override val reviewerId: Long
        get() = dto.reviewerId
}
