package com.jetbrains.life_science.controller.category.view

import com.jetbrains.life_science.controller.approach.view.ApproachViewMapper
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryViewMapper(
    val approachViewMapper: ApproachViewMapper
) {

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

    fun toView(category: Category): CategoryView {
        return CategoryView(
            name = category.name,
            aliases = category.aliases,
            creationDate = category.creationDate,
            subCategories = category.subCategories.map { toViewShort(it) },
            approaches = category.approaches.map { approachViewMapper.toViewShort(it) }
        )
    }
}
