package com.jetbrains.life_science.article.version.adapter

import com.jetbrains.life_science.section.service.SectionCreationInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion

class SectionEmptyCreationToInfoAdapter(
    val version: ArticleVersion
) : SectionCreationInfo {
    override val article: ArticleVersion
        get() = version
    override val name: String
        get() = ""
    override val description: String
        get() = ""
}
