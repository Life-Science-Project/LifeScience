package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.user.entity.User
import com.jetbrains.life_science.article.version.service.ArticleVersionInfo

class ArticleVersionDTOToInfoAdapter(
    val dto: ArticleVersionDTO,
    override val user: User
) : ArticleVersionInfo {

    override val articleId: Long
        get() = dto.articleId

    override val name: String
        get() = dto.name
}
