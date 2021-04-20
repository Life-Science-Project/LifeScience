package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.content.service.ContentService
import com.jetbrains.life_science.article.content.view.ContentViewMapper
import com.jetbrains.life_science.article.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper(
    val contentService: ContentService,
    val contentViewMapper: ContentViewMapper
) {
    fun createView(section: Section): SectionView {
        return SectionView(
            name = section.name,
            description = section.description,
            contents = contentService.findBySectionId(section.id)?.let { contentViewMapper.createView(it) },
            order = section.orderNumber
        )
    }
}
