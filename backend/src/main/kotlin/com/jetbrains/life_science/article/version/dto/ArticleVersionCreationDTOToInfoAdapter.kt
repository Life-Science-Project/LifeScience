package com.jetbrains.life_science.article.version.dto

import com.jetbrains.life_science.article.primary.entity.Article
import com.jetbrains.life_science.article.section.dto.SectionCreationDTOInfoAdapter
import com.jetbrains.life_science.article.section.service.SectionCreationInfo
import com.jetbrains.life_science.article.version.service.ArticleVersionCreationInfo
import com.jetbrains.life_science.user.master.entity.User

class ArticleVersionCreationDTOToInfoAdapter(
    val dto: ArticleVersionCreationDTO,
    override val user: User,
    override val article: Article,
) : ArticleVersionCreationInfo {

    override val name: String = dto.name

    override val sectionsInfo: List<SectionCreationInfo> = dto.sections.map { SectionCreationDTOInfoAdapter(it) }
}
