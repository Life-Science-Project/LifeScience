package com.jetbrains.life_science.controller.section.view

import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper {

    fun toViewAll(sections: List<Section>): List<SectionShortView> {
        return sections.map { toView(it) }
    }

    fun toView(section: Section): SectionShortView {
        return SectionShortView(
            id = section.id,
            name = section.name
        )
    }
}
