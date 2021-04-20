package com.jetbrains.life_science.article.section.factory

import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.parameter.factory.ParameterFactory
import com.jetbrains.life_science.article.section.service.SectionInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class SectionFactory(
    val parameterFactory: ParameterFactory
) {
    fun create(name: String, description: String, article: ArticleVersion): Section {
        return Section(0, name, description, article, mutableListOf())
    }

    fun copy(section: Section): Section {
        return Section(0, section.name, section.description, section.articleVersion, section.parameters)
    }

    fun setParams(origin: Section, info: SectionInfo, version: ArticleVersion) {
        origin.description = info.description
        origin.articleVersion = version
        origin.name = info.name
        origin.parameters = info.parameters.map { parameterFactory.create(it) }.toMutableList()
    }
}
