package com.jetbrains.life_science.content.service

import com.jetbrains.life_science.content.entity.Content
import com.jetbrains.life_science.section.entity.Section

interface ContentService {

    fun create(info: ContentInfo)

    fun updateText(id: String, text: String)

    fun delete(id: String)

    fun deleteBySectionId(sectionId: Long)

    fun findAllBySectionId(sectionId: Long): List<Content>

    fun createCopiesBySection(origin: Section, newSection: Section)
}
