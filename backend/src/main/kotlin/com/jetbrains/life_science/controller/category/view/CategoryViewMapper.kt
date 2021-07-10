package com.jetbrains.life_science.controller.category.view

import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryViewMapper {

    fun toViewShort(category: Category): CategoryShortView {
        return CategoryShortView(
            id = category.id,
            creationDate = category.creationDate,
            name = category.name
        )
    }

    fun toViewsShort(categories: List<Category>): List<CategoryShortView> {
        return categories.map { toViewShort(it) }
    }

}