package com.jetbrains.life_science.approach.view

import com.jetbrains.life_science.approach.entity.Approach
import org.springframework.stereotype.Component

@Component
class ApproachCategoryViewMapper {
    fun createView(approach: Approach): ApproachCategoryView {
        return ApproachCategoryView(
            id = approach.id,
            name = approach.name
        )
    }
}
