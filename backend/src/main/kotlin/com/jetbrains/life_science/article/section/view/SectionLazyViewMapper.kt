package com.jetbrains.life_science.article.section.view

import com.jetbrains.life_science.article.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionLazyViewMapper {
    fun createView(section: Section): SectionLazyView {
        return SectionLazyView(
            id = section.id,
            name = section.name
        )
    }
}