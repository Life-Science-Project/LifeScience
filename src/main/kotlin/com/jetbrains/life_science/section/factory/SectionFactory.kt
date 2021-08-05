package com.jetbrains.life_science.section.factory

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionCreationInfo
import com.jetbrains.life_science.section.service.SectionInfo
import org.springframework.stereotype.Component

@Component
class SectionFactory {
    fun create(info: SectionCreationInfo): Section {
        return Section(
            id = 0,
            name = info.name,
            hidden = info.hidden,
            published = false,
            order = info.prevSection?.let { it.order + 1 } ?: 0
        )
    }

    fun setParams(origin: Section, info: SectionInfo) {
        origin.name = info.name
        origin.hidden = info.visible
        origin.order = info.prevSection?.let { it.order + 1 } ?: 1
    }
}
