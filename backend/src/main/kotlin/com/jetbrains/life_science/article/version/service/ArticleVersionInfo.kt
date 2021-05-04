package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.user.master.entity.User

interface ArticleVersionInfo {

    val id: Long

    val articleId: Long

    val name: String

    val user: User
}
