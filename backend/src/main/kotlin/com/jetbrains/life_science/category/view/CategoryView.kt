package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.article.master.view.ArticleView

class CategoryView(
    val id: Long,
    val parentId: Long?,
    val name: String,
    val order: Int,
    val subcategories: List<CategorySubcategoryView>,
    val articles: List<ArticleView>
)
