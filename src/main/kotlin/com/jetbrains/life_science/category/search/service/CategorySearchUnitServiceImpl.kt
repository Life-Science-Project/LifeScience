package com.jetbrains.life_science.category.search.service

import com.jetbrains.life_science.category.entity.Category
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
        val context = createContext(category)
        val searchUnit = factory.create(category, context)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(category: Category) {
        checkExistsById(category.id)
        val context = createContext(category)
        val searchUnit = factory.create(category, context)
        repository.save(searchUnit)
    }

    override fun getContext(category: Category): List<String> {
        return repository.findById(category.id).get().context
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw CategorySearchUnitNotFoundException("Category search unit not found with id: $id")
        }
    }

    private fun createContext(category: Category): MutableList<String> {
        val context = category.aliases.toMutableList()
        context.add(category.name)
        category.parents.forEach {
            if (it.id != 0L) {
                context.addAll(repository.findById(it.id).get().context)
            }
        }
        return context
    }
}
