package com.jetbrains.life_science.article.review.request.service

import com.jetbrains.life_science.article.review.request.entity.VersionDestination

/**
 * @author Потапов Александр
 * @since 06.05.2021
 */
interface ReviewRequestInfo {

    val versionId: Long

    val destination: VersionDestination
}
