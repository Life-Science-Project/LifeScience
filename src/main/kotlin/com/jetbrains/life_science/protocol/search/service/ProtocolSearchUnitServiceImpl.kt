package com.jetbrains.life_science.protocol.search.service

import com.jetbrains.life_science.protocol.search.factory.ProtocolSearchUnitFactory
import com.jetbrains.life_science.protocol.search.repository.ProtocolSearchUnitRepository
import com.jetbrains.life_science.exception.search_unit.ApproachSearchUnitNotFoundException
import com.jetbrains.life_science.protocol.entity.Protocol
import org.springframework.stereotype.Service

@Service
class ProtocolSearchUnitServiceImpl(
    val repository: ProtocolSearchUnitRepository,
    val factory: ProtocolSearchUnitFactory
) : ProtocolSearchUnitService {
    override fun createSearchUnit(protocol: Protocol) {
        val searchUnit = factory.create(protocol)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(protocol: Protocol) {
        checkExistsById(protocol.id)
        val searchUnit = factory.create(protocol)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ApproachSearchUnitNotFoundException("Approach search unit not found with id: $id")
        }
    }
}
