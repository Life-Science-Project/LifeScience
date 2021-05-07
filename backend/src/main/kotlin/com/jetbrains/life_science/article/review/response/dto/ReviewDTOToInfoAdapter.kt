package com.jetbrains.life_science.article.review.dto

import com.jetbrains.life_science.article.review.response.dto.ReviewDTO
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.service.ReviewInfo
import com.jetbrains.life_science.exception.request.BadRequestException
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.util.enumValueOrNull

class ReviewDTOToInfoAdapter(
    val dto: ReviewDTO,
    override val versionId: Long,
    override val reviewer: User,
    override val reviewId: Long = 0
) : ReviewInfo {

    override val comment: String = dto.comment

    override val resolution: ReviewResolution =
        enumValueOrNull(dto.resolution) ?: throw BadRequestException("Resolution not found")
}
