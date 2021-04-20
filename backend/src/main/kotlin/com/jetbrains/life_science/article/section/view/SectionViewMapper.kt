package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.paragraph.service.ParagraphService
import com.jetbrains.life_science.article.paragraph.view.ParagraphViewMapper
import com.jetbrains.life_science.article.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper(
    val paragraphService: ParagraphService,
    val paragraphViewMapper: ParagraphViewMapper
) {
    fun createView(section: Section): SectionView {
        return SectionView(
            name = section.name,
            description = section.description,
            paragraphs = paragraphService.findAllBySectionId(section.id)
                .map {
                    paragraphViewMapper.createView(it)
                },
            order = section.orderNumber
        )
    }
}
