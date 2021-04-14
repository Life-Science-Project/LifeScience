package com.jetbrains.life_science.article.content.service

import com.jetbrains.life_science.article.content.entity.Content
import com.jetbrains.life_science.article.section.entity.Section

interface ContentService {

    fun create(info: ContentInfo)

    fun updateText(id: String, text: String)

    fun delete(id: String)

    fun deleteBySectionId(sectionId: Long)

    fun findAllBySectionId(sectionId: Long): List<Content>

    fun createCopiesBySection(origin: Section, newSection: Section)
}
