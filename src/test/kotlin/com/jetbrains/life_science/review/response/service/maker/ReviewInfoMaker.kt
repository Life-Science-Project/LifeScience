package com.jetbrains.life_science.review.response.service.maker

import com.jetbrains.life_science.review.response.entity.ReviewResolution
import com.jetbrains.life_science.review.response.service.ReviewInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.util.UTCZone
import java.time.LocalDateTime

fun makeReviewInfo(
    id: Long,
    comment: String,
    resolution: ReviewResolution,
    reviewer: Credentials
) = object : ReviewInfo {
    override val id = id
    override val date = LocalDateTime.now(UTCZone)
    override val comment = comment
    override val resolution = resolution
    override val reviewer = reviewer
}
