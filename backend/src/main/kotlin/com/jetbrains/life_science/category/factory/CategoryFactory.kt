package com.jetbrains.life_science.category.factory

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryInfo
import org.springframework.stereotype.Component

@Component
class CategoryFactory {
    fun createCategory(info: CategoryInfo, parent: Category?): Category {
        return Category(
            id = info.id,
            name = info.name,
            parent = parent,
            subCategories = mutableListOf(),
            articles = mutableListOf(),
            orderNumber = info.order
        )
    }

    fun setParams(category: Category, categoryInfo: CategoryInfo, parent: Category?) {
        category.name = categoryInfo.name
        category.parent = parent
        category.orderNumber = categoryInfo.order
    }
}
