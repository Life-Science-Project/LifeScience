package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.approach.view.PublicApproachCategoryViewMapper
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryViewMapper(
    val approachCategoryViewMapper: PublicApproachCategoryViewMapper
) {
    fun createView(category: Category): CategoryView {
        val subcategoriesView = category.subCategories.map { CategorySubcategoryView(it.id, it.name, it.order) }
        val approachesView = category.approaches
            .map { approachCategoryViewMapper.createView(it) }
        return CategoryView(
            id = category.id,
            name = category.name,
            order = category.order,
            subcategories = subcategoriesView,
            approaches = approachesView
        )
    }
}
