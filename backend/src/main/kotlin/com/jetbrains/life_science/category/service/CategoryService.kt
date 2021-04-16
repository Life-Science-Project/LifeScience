package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category

interface CategoryService {

    fun createCategory(categoryInfo: CategoryInfo): Category

    fun deleteCategory(id: Long)

    fun getCategory(id: Long): Category

    fun getChildren(id: Long): List<Category>

    fun updateCategory(id: Long, categoryInfo: CategoryInfo): Category
}
