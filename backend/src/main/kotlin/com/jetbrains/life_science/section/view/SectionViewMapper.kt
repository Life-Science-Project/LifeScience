package com.jetbrains.life_science.section.view

import com.jetbrains.life_science.content.publish.service.ContentService
import com.jetbrains.life_science.content.publish.view.ContentViewMapper
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper(
    val contentService: ContentService,
    val contentViewMapper: ContentViewMapper
) {
    fun createView(section: Section): SectionView {
        val contents = contentService.findBySectionId(section.id)

        return SectionView(
            id = section.id,
            name = section.name,
            contents = contents?.let { contentViewMapper.createView(it) },
            order = section.order,
            visible = section.visible
        )
    }
}
