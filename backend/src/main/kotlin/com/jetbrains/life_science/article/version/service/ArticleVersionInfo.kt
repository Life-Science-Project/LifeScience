package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.user.details.entity.User

interface ArticleVersionInfo {

    val articleId: Long

    val name: String

    val user: User
}
