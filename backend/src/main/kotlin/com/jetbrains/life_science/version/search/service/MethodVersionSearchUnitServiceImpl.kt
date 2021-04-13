package com.jetbrains.life_science.version.search.service

import com.jetbrains.life_science.exception.search_unit.MethodSearchUnitNotFoundException
import com.jetbrains.life_science.version.entity.MethodVersion
import com.jetbrains.life_science.version.search.factory.MethodSearchUnitFactory
import com.jetbrains.life_science.version.search.repository.MethodVersionSearchUnitRepository
import org.springframework.stereotype.Service

@Service
class MethodVersionSearchUnitServiceImpl(
    val repository: MethodVersionSearchUnitRepository,
    val factory: MethodSearchUnitFactory
) : MethodVersionSearchUnitService {
    override fun createSearchUnit(version: MethodVersion) {
        val searchUnit = factory.create(version)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw MethodSearchUnitNotFoundException("method search unit not found with id $id")
        }
    }
}
