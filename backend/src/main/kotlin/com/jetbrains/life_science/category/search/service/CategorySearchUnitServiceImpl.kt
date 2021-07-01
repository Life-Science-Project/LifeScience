package com.jetbrains.life_science.category.search.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.search.factory.CategorySearchUnitFactory
import com.jetbrains.life_science.category.search.repository.CategorySearchUnitRepository
import com.jetbrains.life_science.exception.not_found.CategorySearchUnitNotFoundException
import org.springframework.stereotype.Service

@Service
class CategorySearchUnitServiceImpl(
    val repository: CategorySearchUnitRepository,
    val factory: CategorySearchUnitFactory
) : CategorySearchUnitService {
    override fun createSearchUnit(category: Category) {
        val searchUnit = factory.create(category)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(category: Category) {
        checkExistsById(category.id)
        val searchUnit = factory.create(category)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw CategorySearchUnitNotFoundException("Category search unit not found with id: $id")
        }
    }
}
