package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.article.master.view.ArticleViewMapper
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryViewMapper(
    val articleViewMapper: ArticleViewMapper
) {
    fun createView(category: Category): CategoryView {
        val subcategoriesView = category.subCategories.map { CategorySubcategoryView(it.id, it.name, it.orderNumber) }
        val articlesView = category.articles.filter { it.versions.isNotEmpty() }.map { articleViewMapper.createView(it) }
        return CategoryView(
            id = category.id,
            parentId = category.parent?.id,
            name = category.name,
            order = category.orderNumber,
            subcategories = subcategoriesView,
            articles = articlesView
        )
    }
}
