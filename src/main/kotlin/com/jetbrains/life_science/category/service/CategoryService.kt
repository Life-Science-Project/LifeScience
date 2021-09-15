package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category

interface CategoryService {

    fun createCategory(categoryInfo: CategoryInfo): Category

    fun deleteCategory(id: Long)

    fun getById(id: Long): Category

    fun updateCategory(categoryInfo: CategoryUpdateInfo): Category

    fun getRootCategories(): List<Category>
}
