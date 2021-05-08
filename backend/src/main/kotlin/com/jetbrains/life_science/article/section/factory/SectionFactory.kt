package com.jetbrains.life_science.article.section.factory

import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.parameter.factory.ParameterFactory
import com.jetbrains.life_science.article.section.parameter.service.ParameterService
import com.jetbrains.life_science.article.section.service.SectionInfo
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import org.springframework.stereotype.Component

@Component
class SectionFactory(
    val parameterFactory: ParameterFactory,
    val parameterService: ParameterService
) {
    fun create(info: SectionInfo, article: ArticleVersion): Section {
        return Section(
            id = 0,
            name = info.name,
            description = info.description,
            articleVersion = article,
            parameters = mutableListOf(),
            orderNumber = info.order,
            visible = info.visible
        )
    }

    fun copy(section: Section): Section {
        return Section(
            id = 0,
            name = section.name,
            description = section.description,
            articleVersion = section.articleVersion,
            parameters = section.parameters.toMutableList(),
            orderNumber = section.orderNumber,
            visible = section.visible
        )
    }

    fun setParams(origin: Section, info: SectionInfo, version: ArticleVersion) {
        origin.description = info.description
        origin.articleVersion = version
        origin.name = info.name
        origin.parameters = info.parameters.map {
            parameterService.create(it)
        }.toMutableList()
        origin.orderNumber = info.order
        origin.visible = info.visible
    }
}
