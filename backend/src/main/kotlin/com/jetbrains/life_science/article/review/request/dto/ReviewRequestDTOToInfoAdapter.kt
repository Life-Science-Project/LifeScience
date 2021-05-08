package com.jetbrains.life_science.article.review.request.dto

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestInfo

class ReviewRequestDTOToInfoAdapter(
    override val versionId: Long,
    override val destination: VersionDestination
) : ReviewRequestInfo
