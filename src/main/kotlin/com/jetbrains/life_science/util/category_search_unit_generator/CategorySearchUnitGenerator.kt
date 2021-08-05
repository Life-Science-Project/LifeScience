package com.jetbrains.life_science.util.category_search_unit_generator

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.search.PathUnit
import com.jetbrains.life_science.category.search.Path
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
            createUnitDfs(it, mutableSetOf(), listOf(listOf()), visited)
        }
    }

    private fun createUnitDfs(
        category: Category,
        previousContext: MutableSet<String>,
        previousPaths: List<Path>,
        visited: MutableSet<Long>
    ) {
        if (visited.contains(category.id)) {
            updateContextDfs(category, previousContext, previousPaths)
        } else {
            visited.add(category.id)
            val currentContext = generateContext(category, previousContext)
            val categorySearchUnit =
                factory.create(
                    category,
                    currentContext.toList(),
                    previousPaths
                )
            categorySearchUnitRepository.save(categorySearchUnit)
            val newPaths = generatePaths(category, previousPaths)
            category.subCategories.forEach {
                createUnitDfs(it, currentContext, newPaths, visited)
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

    private fun generatePaths(category: Category, currentPaths: List<Path>): List<Path> {
        return currentPaths.map {
            it.toMutableList().also { newPath ->
                newPath.add(PathUnit(category.id, category.name))
            }
        }
    }

    private fun updateContextDfs(category: Category, previousContext: MutableSet<String>, previousPaths: List<Path>) {
        val searchUnit = categorySearchUnitRepository.findById(category.id).orElseThrow {
            CategorySearchUnitNotFoundException("Category search unit ${category.id} is not found")
        }
        val currentContext = searchUnit.context.toMutableSet()
        val currentPaths = searchUnit.paths.toMutableList()
        currentContext.addAll(previousContext)
        currentPaths.addAll(previousPaths)
        val categorySearchUnit = factory.create(category, currentContext.toList(), currentPaths)
        categorySearchUnitRepository.save(categorySearchUnit)
        val newPaths = generatePaths(category, previousPaths)
        category.subCategories.forEach {
            updateContextDfs(it, currentContext, newPaths)
        }
    }
}
