package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.publish.view.ContentViewMapper
import com.jetbrains.life_science.article.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper(
    val contentService: ContentService,
    val contentViewMapper: ContentViewMapper
) {
    fun createView(section: Section): SectionView {
        return SectionView(
            articleVersionId = section.articleVersion.id,
            name = section.name,
            description = section.description,
            contents = contentService.findBySectionId(section.id)?.let { contentViewMapper.createView(it) },
            order = section.orderNumber,
            visible = section.visible
        )
    }
}
