package com.jetbrains.life_science.article.review.response.dto

import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.service.ReviewInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.exception.request.BadRequestException
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.util.enumValue

class ReviewDTOToInfoAdapter(
    val dto: ReviewDTO,
    override val version: ArticleVersion,
    override val reviewer: User,
    ) : ReviewInfo {

    override val comment: String = dto.comment

    override val resolution: ReviewResolution =
        enumValue<ReviewResolution>(dto.resolution) ?: throw BadRequestException("Invalid resolution")
}
