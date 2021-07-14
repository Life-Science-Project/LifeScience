package com.jetbrains.life_science.review.response.service

import com.jetbrains.life_science.review.response.entity.ReviewResolution
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

interface ReviewInfo {
    val id: Long
    val date: LocalDateTime
    val comment: String
    val resolution: ReviewResolution
    val reviewer: Credentials
}
