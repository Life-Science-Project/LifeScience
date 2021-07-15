package com.jetbrains.life_science.approach.search.service

import com.jetbrains.life_science.approach.entity.Approach
import com.jetbrains.life_science.approach.search.factory.ApproachSearchUnitFactory
import com.jetbrains.life_science.approach.search.repository.ApproachSearchUnitRepository
import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.exception.search_unit.ApproachSearchUnitNotFoundException
import org.springframework.stereotype.Service

@Service
class ApproachSearchUnitServiceImpl(
    val repository: ApproachSearchUnitRepository,
    val factory: ApproachSearchUnitFactory,
    val categorySearchUnitService: CategorySearchUnitService
) : ApproachSearchUnitService {
    override fun createSearchUnit(approach: Approach) {
        val context = createContext(approach)
        val searchUnit = factory.create(approach, context)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(approach: Approach) {
        checkExistsById(approach.id)
        val context = createContext(approach)
        val searchUnit = factory.create(approach, context)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ApproachSearchUnitNotFoundException("Approach search unit not found with id: $id")
        }
    }

    private fun createContext(approach: Approach): List<String> {
        val context = mutableListOf(approach.name)
        approach.categories.forEach {
            context.addAll(categorySearchUnitService.getContext(it))
        }
        return context
    }
}
