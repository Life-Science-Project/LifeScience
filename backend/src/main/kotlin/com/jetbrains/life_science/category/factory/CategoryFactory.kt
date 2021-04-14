package com.jetbrains.life_science.category.factory

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.entity.CategoryInfo
import org.springframework.stereotype.Component

@Component
class CategoryFactory {
    fun createSection(categoryInfo: CategoryInfo, parent: Category?): Category {
        return Category(categoryInfo.getID(), categoryInfo.getName(), parent)
    }
}
