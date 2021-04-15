package com.jetbrains.life_science.category.view

import com.jetbrains.life_science.article.master.entity.Article
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.category.entity.Category
import org.springframework.stereotype.Component

@Component
class CategoryViewMapper {
    fun createView(category: Category, subcategories: List<Category>, articles: List<Article>): CategoryView {
        val subcategoriesView = subcategories.map { CategorySubcategoryView(it.id, it.name) }
        val articlesView = articles.map { ArticleView(it.id) }
        return CategoryView(category.id, category.parent?.id, category.name, subcategoriesView, articlesView)
    }
}
