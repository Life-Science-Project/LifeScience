package com.jetbrains.life_science.category.search.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.search.CategorySearchUnit
import com.jetbrains.life_science.category.search.PathUnit
import com.jetbrains.life_science.category.search.Path
import com.jetbrains.life_science.category.search.factory.CategorySearchUnitFactory
import com.jetbrains.life_science.category.search.repository.CategorySearchUnitRepository
import com.jetbrains.life_science.exception.search_unit.CategorySearchUnitNotFoundException
import org.springframework.stereotype.Service

@Service
class CategorySearchUnitServiceImpl(
    val repository: CategorySearchUnitRepository,
    val factory: CategorySearchUnitFactory
) : CategorySearchUnitService {
    override fun createSearchUnit(category: Category) {
        val parentsSearchUnits = getParentsSearchUnits(category)
        val context = createContext(category, parentsSearchUnits)
        val searchUnit = factory.create(category, context, createPaths(category, parentsSearchUnits))
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(category: Category) {
        checkExistsById(category.id)
        val parentsSearchUnits = getParentsSearchUnits(category)
        val context = createContext(category, parentsSearchUnits)
        val searchUnit = factory.create(category, context, createPaths(category, parentsSearchUnits))
        repository.save(searchUnit)
    }

    override fun getContext(category: Category): List<String> {
        return repository.findById(category.id)
            .orElseThrow { CategorySearchUnitNotFoundException("Category Search Unit with id ${category.id} not found") }
            .context
    }

    override fun getPaths(category: Category): List<Path> {
        return repository.findById(category.id)
            .orElseThrow { CategorySearchUnitNotFoundException("Category Search Unit with id ${category.id} not found") }
            .paths
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw CategorySearchUnitNotFoundException("Category search unit not found with id: $id")
        }
    }

    private fun createContext(category: Category, parentsSearchUnits: List<CategorySearchUnit>): List<String> {
        val context = category.aliases.toMutableSet()
        context.add(category.name)
        parentsSearchUnits.forEach {
            context.addAll(it.context)
        }
        return context.toList()
    }

    private fun createPaths(category: Category, parentsSearchUnits: List<CategorySearchUnit>): MutableList<Path> {
        if (category.parents.isEmpty()) {
            return mutableListOf(emptyList())
        }
        val paths = mutableListOf<Path>()
        parentsSearchUnits.forEach {
            for (oldPath in it.paths) {
                val modifiedPath = oldPath.toMutableList()
                modifiedPath.add(PathUnit(it.id, it.names[0]))
                paths.add(modifiedPath)
            }
        }
        return paths
    }

    private fun getParentsSearchUnits(category: Category): List<CategorySearchUnit> {
        val parentsSearchUnits = category.parents
            .filter { it.id != 0L }
            .map {
                repository.findById(it.id).orElseThrow {
                    throw CategorySearchUnitNotFoundException("Category search unit not found with id: ${it.id}")
                }
            }
        return parentsSearchUnits
    }
}
