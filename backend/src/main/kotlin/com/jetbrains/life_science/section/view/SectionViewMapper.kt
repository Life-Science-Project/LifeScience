package com.jetbrains.life_science.section.view

import com.jetbrains.life_science.content.service.ContentService
import com.jetbrains.life_science.content.view.ContentViewMapper
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper(
    val contentService: ContentService,
    val contentViewMapper: ContentViewMapper
) {
    fun createView(section: Section): SectionView {
        return SectionView(
            section.name,
            section.description,
            contentService.findAllBySectionId(section.id)
                .map {
                    contentViewMapper.createView(it)
                }
        )
    }
}
