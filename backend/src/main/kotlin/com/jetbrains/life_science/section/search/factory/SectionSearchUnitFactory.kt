package com.jetbrains.life_science.section.search.factory

import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.search.SectionSearchUnit
import org.springframework.stereotype.Component

@Component
class SectionSearchUnitFactory {

    fun create(section: Section) = SectionSearchUnit(section.id, section.description ?: "")
}
