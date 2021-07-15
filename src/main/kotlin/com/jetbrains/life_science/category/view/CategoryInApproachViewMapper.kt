package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryInApproachViewMapper {
    fun createView(category: Category): CategoryInApproachView {
        return CategoryInApproachView(
            id = category.id,
            name = category.name
        )
    }
}
