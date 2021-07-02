package com.jetbrains.life_science.category.search.service

import com.jetbrains.life_science.category.entity.Category

interface CategorySearchUnitService {

    fun createSearchUnit(category: Category)

    fun deleteSearchUnitById(id: Long)

    fun update(category: Category)
}
