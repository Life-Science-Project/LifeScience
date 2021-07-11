package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.controller.category.dto.CategoryUpdateDTOToInfoAdapter

interface CategoryService {

    fun createCategory(categoryInfo: CategoryInfo): Category

    fun deleteCategory(id: Long)

    fun getCategory(id: Long): Category

    fun updateCategory(categoryInfo: CategoryUpdateInfo): Category

    fun getRootCategories(): List<Category>
}
