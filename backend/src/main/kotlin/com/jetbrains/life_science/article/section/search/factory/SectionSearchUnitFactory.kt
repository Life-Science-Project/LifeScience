package com.jetbrains.life_science.article.section.search.factory

import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.search.SectionSearchUnit
import org.springframework.stereotype.Component

@Component
class SectionSearchUnitFactory {

    fun create(section: Section) = SectionSearchUnit(section.id, section.description ?: "")
}
