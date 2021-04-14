package com.jetbrains.life_science.category.view

// TODO(#54): add articles
class CategoryView(
    val id: Long?,
    val parentId: Long?,
    val name: String,
    val children: List<CategoryChildrenView> // TODO(#54): maybe children -> subcategories?
)
