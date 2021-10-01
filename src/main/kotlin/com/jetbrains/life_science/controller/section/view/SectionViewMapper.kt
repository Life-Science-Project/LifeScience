package com.jetbrains.life_science.controller.section.view

import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper {

    fun toView(section: Section, contentText: String? = null) =
        SectionView(
            id = section.id,
            name = section.name,
            hidden = section.hidden,
            content = contentText
        )

    fun toViewAll(sections: List<Section>) =
        sections.sortedBy { it.order }.map { toView(it) }

    fun toViewShort(section: Section) =
        SectionShortView(
            id = section.id,
            name = section.name
        )

    fun toViewShortAll(sections: List<Section>) =
        sections.sortedBy { it.order }.map { toViewShort(it) }
}
