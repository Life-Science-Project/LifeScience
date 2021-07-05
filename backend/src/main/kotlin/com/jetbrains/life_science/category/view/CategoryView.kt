package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.approach.view.ApproachCategoryView

data class CategoryView(
    val id: Long,
    val parentId: Long?,
    val name: String,
    val order: Long,
    val subcategories: List<CategorySubcategoryView>,
    val approaches: List<ApproachCategoryView>
)
