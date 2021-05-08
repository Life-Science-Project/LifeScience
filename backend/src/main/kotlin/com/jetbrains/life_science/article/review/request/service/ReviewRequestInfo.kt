package com.jetbrains.life_science.article.review.request.service

import com.jetbrains.life_science.article.review.request.entity.VersionDestination

interface ReviewRequestInfo {

    val versionId: Long

    val destination: VersionDestination
}
