package com.jetbrains.life_science.section.view

import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionViewMapper {
    fun createView(section: Section, children: List<Section>): SectionView {
        val childrenView = children.map { SectionChildrenView(it.id, it.name) }
        return SectionView(section.id, section.parent?.id, section.name, childrenView)
    }
}