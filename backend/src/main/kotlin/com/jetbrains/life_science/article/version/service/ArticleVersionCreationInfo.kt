package com.jetbrains.life_science.article.version.service

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.section.service.SectionCreationInfo
import com.jetbrains.life_science.user.master.entity.User

interface ArticleVersionCreationInfo {

    val article: Article

    val name: String

    val user: User

    val sectionsInfo: List<SectionCreationInfo>
}
