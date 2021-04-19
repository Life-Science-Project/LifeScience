package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.version.service.ArticleVersionInfo
import com.jetbrains.life_science.user.details.entity.User

class ArticleVersionDTOToInfoAdapter(
    val dto: ArticleVersionDTO,
    override val user: User,
    override val id: Long = 0
) : ArticleVersionInfo {

    override val articleId: Long
        get() = dto.articleId

    override val name: String
        get() = dto.name
}
