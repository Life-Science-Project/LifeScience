package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.publish.view.ContentViewMapper
import com.jetbrains.life_science.article.content.version.service.ContentVersionService
import com.jetbrains.life_science.article.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper(
    val contentService: ContentService,
    val contentVersionService: ContentVersionService,
    val contentViewMapper: ContentViewMapper
) {
    fun createView(section: Section): SectionView {

        val contents = if (section.articleVersion.isPublished) {
            contentService.findBySectionId(section.id)
        } else {
            contentVersionService.findBySectionId(section.id)
        }

        return SectionView(
            id = section.id,
            articleVersionId = section.articleVersion.id,
            name = section.name,
            description = section.description,
            contents = contents?.let { contentViewMapper.createView(it) },
            order = section.orderNumber,
            visible = section.visible
        )
    }
}
