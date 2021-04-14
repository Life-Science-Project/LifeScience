package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryViewMapper {
    fun createView(category: Category, children: List<Category>): CategoryView {
        val childrenView = children.map { CategoryChildrenView(it.id, it.name) }
        return CategoryView(category.id, category.parent?.id, category.name, childrenView)
    }
}
