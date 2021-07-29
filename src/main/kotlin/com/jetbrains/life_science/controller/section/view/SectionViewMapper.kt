package com.jetbrains.life_science.controller.section.view

import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper {

    fun toView(section: Section, contentText: String? = null): SectionView {
        return SectionView(
            id = section.id,
            name = section.name,
            hidden = !section.visible,
            content = contentText
        )
    }

    fun toViewShortAll(sections: List<Section>): List<SectionShortView> {
        return sections.sortedBy { it.order }.map { toViewShort(it) }
    }

    fun toViewShort(section: Section): SectionShortView {
        return SectionShortView(
            id = section.id,
            name = section.name
        )
    }
}
