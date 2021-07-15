package com.jetbrains.life_science.category.search.factory

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.search.CategorySearchUnit
import org.springframework.stereotype.Component

@Component
class CategorySearchUnitFactory {
    fun create(category: Category, context: List<String>): CategorySearchUnit {
        val names = category.aliases.toMutableList()
        names.add(category.name)
        return CategorySearchUnit(category.id, names, context)
    }
}
