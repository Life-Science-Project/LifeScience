package com.jetbrains.life_science.approach.search.service

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.approach.search.factory.ApproachSearchUnitFactory
import com.jetbrains.life_science.approach.search.repository.ApproachSearchUnitRepository
import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.exception.search_unit.ApproachSearchUnitNotFoundException
import com.jetbrains.life_science.protocol.search.service.ProtocolSearchUnitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApproachSearchUnitServiceImpl(
    val repository: ApproachSearchUnitRepository,
    val factory: ApproachSearchUnitFactory,
    val categorySearchUnitService: CategorySearchUnitService
) : ApproachSearchUnitService {

    @Autowired
    lateinit var protocolSearchUnitService: ProtocolSearchUnitService

    override fun createSearchUnit(approach: PublicApproach) {
        val context = createContext(approach)
        val searchUnit = factory.create(approach, context)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(approach: PublicApproach) {
        checkExistsById(approach.id)
        val context = createContext(approach)
        val searchUnit = factory.create(approach, context)
        approach.protocols.forEach {
            protocolSearchUnitService.update(it)
        }
        repository.save(searchUnit)
    }

    override fun getContext(approachId: Long): List<String> {
        return repository.findById(approachId)
            .orElseThrow { ApproachSearchUnitNotFoundException("Approach Search Unit with id $approachId not found") }
            .context
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ApproachSearchUnitNotFoundException("Approach search unit not found with id: $id")
        }
    }

    private fun createContext(approach: PublicApproach): List<String> {
        val context = mutableListOf(approach.name)
        approach.categories.forEach {
            if (it.id != 0L) {
                context.addAll(categorySearchUnitService.getContext(it))
            }
        }
        return context
    }
}
