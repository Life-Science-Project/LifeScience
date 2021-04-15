package com.jetbrains.life_science.article.review.dto

import com.jetbrains.life_science.article.review.service.ArticleReviewInfo

class ArticleReviewDTOToInfoAdapter(
    val dto: ArticleReviewDTO
) : ArticleReviewInfo {
    override val articleVersionId: Long
        get() = dto.articleVersionId
    override val comment: String
        get() = dto.comment
    override val reviewerId: Long
        get() = dto.authorId
}
