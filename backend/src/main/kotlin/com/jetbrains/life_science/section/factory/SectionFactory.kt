package com.jetbrains.life_science.section.factory

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.service.SectionInfo
import org.springframework.stereotype.Component

@Component
class SectionFactory {
    fun create(info: SectionInfo): Section {
        return Section(
            id = 0,
            name = info.name,
            order = info.order,
            visible = info.visible,
            published = false
        )
    }

    fun copy(section: Section): Section {
        return Section(
            id = 0,
            name = section.name,
            order = section.order,
            visible = section.visible,
            published = false
        )
    }

    fun setParams(origin: Section, info: SectionInfo) {
        origin.name = info.name
        origin.order = info.order
        origin.visible = info.visible
    }
}
