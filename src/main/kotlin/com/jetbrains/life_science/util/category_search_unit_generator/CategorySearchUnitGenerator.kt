package com.jetbrains.life_science.util.category_search_unit_generator

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.search.factory.CategorySearchUnitFactory
import com.jetbrains.life_science.category.search.repository.CategorySearchUnitRepository
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.exception.search_unit.CategorySearchUnitNotFoundException
import org.springframework.stereotype.Component

@Component
class CategorySearchUnitGenerator(
    val categoryService: CategoryService,
    val categorySearchUnitRepository: CategorySearchUnitRepository,
    val factory: CategorySearchUnitFactory
) {
    fun generate() {
        val rootCategories = categoryService.getRootCategories()
        val visited = mutableSetOf<Long>()
        rootCategories.forEach {
            createUnitDfs(it, mutableSetOf(), visited)
        }
    }

    private fun createUnitDfs(category: Category, previousContext: MutableSet<String>, visited: MutableSet<Long>) {
        if (visited.contains(category.id)) {
            updateContextDfs(category, previousContext)
        } else {
            visited.add(category.id)
            val currentContext = generateContext(category, previousContext)
            val categorySearchUnit = factory.create(category, currentContext.toList())
            categorySearchUnitRepository.save(categorySearchUnit)
            category.subCategories.forEach {
                createUnitDfs(it, currentContext, visited)
            }
        }
    }

    private fun generateContext(category: Category, currentContext: Set<String>): MutableSet<String> {
        val context = category.aliases.toMutableSet()
        with(context) {
            add(category.name)
            addAll(currentContext)
            return this
        }
    }

    private fun updateContextDfs(category: Category, previousContext: MutableSet<String>) {
        val currentContext = categorySearchUnitRepository.findById(category.id).orElseThrow {
            CategorySearchUnitNotFoundException("Category search unit ${category.id} is not found")
        }.context.toMutableSet()
        currentContext.addAll(previousContext)
        val categorySearchUnit = factory.create(category, currentContext.toList())
        categorySearchUnitRepository.save(categorySearchUnit)
        category.subCategories.forEach {
            updateContextDfs(it, currentContext)
        }
    }
}
