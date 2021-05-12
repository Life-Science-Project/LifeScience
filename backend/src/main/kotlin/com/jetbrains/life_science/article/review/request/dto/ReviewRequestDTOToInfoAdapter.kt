package com.jetbrains.life_science.article.review.request.dto

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User

class ReviewRequestDTOToInfoAdapter(
    val dto: ReviewRequestDTO,
    override val version: ArticleVersion,
    override val user: User
) : ReviewRequestInfo {

    override val destination: VersionDestination = enumValueOf(dto.destination)
}
