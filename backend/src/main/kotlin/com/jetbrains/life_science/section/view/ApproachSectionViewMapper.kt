package com.jetbrains.life_science.section.view

import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.publish.view.ContentViewMapper
import com.jetbrains.life_science.section.entity.ApproachSection
import org.springframework.stereotype.Component

@Component
class ApproachSectionViewMapper(
    val contentService: ContentService,
    val contentViewMapper: ContentViewMapper
) {
    fun createView(section: ApproachSection): ApproachSectionView {
        val contents = contentService.findBySectionId(section.id)

        return ApproachSectionView(
            id = section.id,
            approachId = section.approach.id,
            name = section.name,
            contents = contents?.let { contentViewMapper.createView(it) },
            order = section.order,
            visible = section.visible
        )
    }
}
