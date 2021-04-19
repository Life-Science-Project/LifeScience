package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.article.master.view.ArticleViewMapper
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryViewMapper(
    val articleViewMapper: ArticleViewMapper
) {
    fun createView(category: Category): CategoryView {
        val subcategoriesView = category.subCategories.map { CategorySubcategoryView(it.id, it.name) }
        val articlesView = category.articles.map { articleViewMapper.createView(it) }
        return CategoryView(category.id, category.parent?.id, category.name, subcategoriesView, articlesView)
    }
}
