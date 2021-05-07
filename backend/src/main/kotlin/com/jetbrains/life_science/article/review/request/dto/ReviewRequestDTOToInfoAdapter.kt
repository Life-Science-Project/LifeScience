package com.jetbrains.life_science.article.review.request.dto

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestInfo

/**
 * @author Потапов Александр
 * @since 07.05.2021
 */
class ReviewRequestDTOToInfoAdapter(
    override val versionId: Long,
    override val destination: VersionDestination
) : ReviewRequestInfo
