package com.jetbrains.life_science.category.factory

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryInfo
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CategoryFactory {
    fun createCategory(info: CategoryInfo, parent: Category?): Category {
        val parents = parent?.let { mutableListOf(it) } ?: mutableListOf()
        return Category(
            id = info.id,
            name = info.name,
            subCategories = mutableListOf(),
            approaches = mutableListOf(),
            parents = parents,
            creationDate = LocalDateTime.now()
        )
    }

    fun setParams(category: Category, categoryInfo: CategoryInfo, parent: Category?) {
        category.name = categoryInfo.name
    }
}
