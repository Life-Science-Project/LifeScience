package com.jetbrains.life_science.article.section.factory

import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.service.SectionCreationInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class SectionFactory {
    fun create(name: String, description: String, article: ArticleVersion): Section {
        return Section(0, name, description, article)
    }

    fun copy(section: Section): Section {
        return Section(0, section.name, section.description, section.article)
    }

    fun createByVersion(info: SectionCreationInfo): Section {
        return Section(0, info.name, info.description, info.article)
    }
}
