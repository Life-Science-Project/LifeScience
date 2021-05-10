package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.publish.view.ContentViewMapper
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.parameter.view.ParameterViewMapper
import org.springframework.stereotype.Component

@Component
class SectionViewMapper(
    val contentService: ContentService,
    val contentViewMapper: ContentViewMapper,
    val parameterViewMapper: ParameterViewMapper,
) {
    fun createView(section: Section): SectionView {
        return SectionView(
            id = section.id,
            articleVersionId = section.articleVersion.id,
            name = section.name,
            description = section.description,
            contents = contentService.findBySectionId(section.id)?.let { contentViewMapper.createView(it) },
            order = section.orderNumber,
            visible = section.visible
        )
    }
}
