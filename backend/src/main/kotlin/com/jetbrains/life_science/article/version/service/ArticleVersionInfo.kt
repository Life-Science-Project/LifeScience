package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.user.credentials.entity.UserCredentials

interface ArticleVersionInfo {

    val articleId: Long

    val name: String

    val userCredentials: UserCredentials
}
