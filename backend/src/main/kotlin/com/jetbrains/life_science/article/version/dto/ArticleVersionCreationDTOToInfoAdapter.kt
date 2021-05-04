package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.master.dto.ArticleDTOToInfoAdapter
import com.jetbrains.life_science.article.version.service.ArticleVersionCreationInfo
import com.jetbrains.life_science.user.master.entity.User

class ArticleVersionCreationDTOToInfoAdapter(
    val dto: ArticleVersionCreationDTO,
    override val user: User,
    override val id: Long = 0
) : ArticleVersionCreationInfo {

    override val articleInfo = ArticleDTOToInfoAdapter(dto.articleDTO)

    override val name = dto.name
}
