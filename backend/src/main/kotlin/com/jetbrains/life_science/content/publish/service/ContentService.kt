package com.jetbrains.life_science.content.publish.service

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.section.entity.Section

interface ContentService {

    fun delete(id: String)

    fun deleteBySectionId(sectionId: Long)

    fun findBySectionId(sectionId: Long): Content?

    fun findById(contentId: String?): Content

    fun createCopyBySection(origin: Section, newSection: Section)

    fun publishBySectionId(sectionId: Long)
}
