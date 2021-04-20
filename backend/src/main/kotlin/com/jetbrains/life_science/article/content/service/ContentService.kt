package com.jetbrains.life_science.article.content.service

import com.jetbrains.life_science.article.content.entity.Content
import com.jetbrains.life_science.article.section.entity.Section

interface ContentService {

    fun create(info: ContentInfo): Content

    fun update(info: ContentInfo): Content

    fun updateText(id: String, text: String): Content

    fun delete(id: String)

    fun deleteBySectionId(sectionId: Long)

    fun findBySectionId(sectionId: Long): Content?

    fun findById(contentId: String?): Content

    fun createCopyBySection(origin: Section, newSection: Section)
}
