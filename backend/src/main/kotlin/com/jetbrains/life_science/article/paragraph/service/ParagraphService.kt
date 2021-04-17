package com.jetbrains.life_science.article.paragraph.service

import com.jetbrains.life_science.article.paragraph.entity.Paragraph
import com.jetbrains.life_science.article.section.entity.Section

interface ParagraphService {

    fun create(info: ParagraphInfo): Paragraph

    fun update(info: ParagraphInfo): Paragraph

    fun updateText(id: String, text: String): Paragraph

    fun delete(id: String)

    fun deleteBySectionId(sectionId: Long)

    fun findAllBySectionId(sectionId: Long): List<Paragraph>

    fun findById(paragraphId: String?): Paragraph

    fun createCopiesBySection(origin: Section, newSection: Section)
}
