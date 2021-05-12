package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.version.service.ArticleVersionCreationInfo
import com.jetbrains.life_science.user.master.entity.User

class ArticleVersionFullCreationDTOToInfoAdapter(
    val dto: ArticleVersionFullCreationDTO,
    override val user: User,
    override val article: Article
) : ArticleVersionCreationInfo {

    override val name = dto.name
}
