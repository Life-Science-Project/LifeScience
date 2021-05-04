package com.jetbrains.life_science.article.section.search.factory

import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.search.SectionSearchUnit
import org.springframework.stereotype.Component

@Component
class SectionSearchUnitFactory {

    fun create(section: Section) =
        SectionSearchUnit(
            id = section.id,
            description = section.description ?: "",
            articleVersionId = section.articleVersion.id
        )
}
