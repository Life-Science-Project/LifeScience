package com.jetbrains.life_science.article.review.request.service

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.user.master.entity.User

interface ReviewRequestInfo {

    val version: ArticleVersion

    val user: User

    val destination: VersionDestination
}
