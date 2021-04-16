package com.jetbrains.life_science.category.factory

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryInfo
import org.springframework.stereotype.Component

@Component
class CategoryFactory {
    fun createSection(categoryInfo: CategoryInfo, parent: Category?): Category {
        return Category(categoryInfo.getId(), categoryInfo.getName(), parent)
    }

    fun setParams(category: Category, categoryInfo: CategoryInfo, parent: Category?) {
        category.name = categoryInfo.getName()
        category.parent = parent
    }
}
