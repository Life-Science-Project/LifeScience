package com.jetbrains.life_science.category.view

class CategoryView(
    val id: Long?,
    val parentId: Long?,
    val name: String,
    val children: List<CategoryChildrenView>
)
