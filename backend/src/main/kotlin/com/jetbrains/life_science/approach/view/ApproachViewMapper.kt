package com.jetbrains.life_science.approach.view

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.category.view.CategoryInApproachViewMapper
import com.jetbrains.life_science.section.view.SectionViewMapper
import org.springframework.stereotype.Component

@Component
class ApproachViewMapper(
    val categoryInApproachViewMapper: CategoryInApproachViewMapper,
    val sectionViewMapper: SectionViewMapper
) {
    fun createView(approach: Approach): ApproachView {
        return ApproachView(
            id = approach.id,
            name = approach.name,
            categories = approach.categories.map { categoryInApproachViewMapper.createView(it) },
            sections = approach.sections.map { sectionViewMapper.createView(it) }
        )
    }
}
