package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.master.service.ArticleInfo
import com.jetbrains.life_science.user.master.entity.User

interface ArticleVersionCreationInfo {

    val id: Long

    val articleInfo: ArticleInfo

    val name: String

    val user: User
}
