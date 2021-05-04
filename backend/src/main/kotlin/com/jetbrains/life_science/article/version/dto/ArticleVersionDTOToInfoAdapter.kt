package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.version.service.ArticleVersionInfo
import com.jetbrains.life_science.user.master.entity.User

class ArticleVersionDTOToInfoAdapter(
    val dto: ArticleVersionDTO,
    override val user: User,
    override val id: Long = 0
) : ArticleVersionInfo {

    override val articleId = dto.articleId

    override val name = dto.name
}
