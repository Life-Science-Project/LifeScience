package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import com.jetbrains.life_science.article.version.service.ArticleVersionInfo

class ArticleVersionDTOToInfoAdapter(
    val dto: ArticleVersionDTO,
    override val userCredentials: UserCredentials
) : ArticleVersionInfo {

    override val articleId: Long
        get() = dto.articleId

    override val name: String
        get() = dto.name
}
