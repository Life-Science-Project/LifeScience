package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.article.version.entity.ArticleVersion

interface SectionService {

    fun createBlankByVersion(info: SectionCreationInfo): Section

    fun create(info: SectionInfo): Section

    fun deleteById(id: Long)

    fun getById(id: Long): Section

    fun checkExistsById(id: Long)

    fun update(into: SectionUpdateInfo)

    fun createCopiesByArticle(article: ArticleVersion, newArticle: ArticleVersion)

    fun deleteSearchUnits(oldSections: List<Section>)

    fun createSearchUnits(newSections: List<Section>)
}
