package com.jetbrains.life_science.category.search.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.search.Path

interface CategorySearchUnitService {

    fun createSearchUnit(category: Category)

    fun deleteSearchUnitById(id: Long)

    fun update(category: Category)

    fun getContext(category: Category): List<String>

    fun getPaths(category: Category): List<Path>
}
