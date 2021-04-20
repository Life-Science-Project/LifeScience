package com.jetbrains.life_science.category.factory

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryInfo
import org.springframework.stereotype.Component

@Component
class CategoryFactory {
    fun createCategory(categoryInfo: CategoryInfo, parent: Category?): Category {
        return Category(categoryInfo.id, categoryInfo.name, parent, mutableListOf(), mutableListOf())
    }

    fun setParams(category: Category, categoryInfo: CategoryInfo, parent: Category?) {
        category.name = categoryInfo.name
        category.parent = parent
    }
}
