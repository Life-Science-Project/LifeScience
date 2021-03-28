package com.jetbrains.life_science.section.factory

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.entity.SectionInfo
import org.springframework.stereotype.Component

@Component
class SectionFactory {
    fun createSection(sectionInfo: SectionInfo, parent: Section?): Section {
        return Section(sectionInfo.getID(), sectionInfo.getName(), parent)
    }
}
