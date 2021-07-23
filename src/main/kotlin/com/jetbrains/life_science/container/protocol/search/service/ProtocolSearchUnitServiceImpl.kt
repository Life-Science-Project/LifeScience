package com.jetbrains.life_science.container.protocol.search.service

import com.jetbrains.life_science.container.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.container.protocol.search.factory.ProtocolSearchUnitFactory
import com.jetbrains.life_science.container.protocol.search.repository.ProtocolSearchUnitRepository
import com.jetbrains.life_science.exception.search_unit.ApproachSearchUnitNotFoundException
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProtocolSearchUnitServiceImpl(
    val repository: ProtocolSearchUnitRepository,
    val factory: ProtocolSearchUnitFactory
) : ProtocolSearchUnitService {

    @Autowired
    lateinit var approachSearchUnitService: ApproachSearchUnitService

    override fun createSearchUnit(protocol: PublicProtocol) {
        val context = createContext(protocol)
        val searchUnit = factory.create(protocol, context)
        repository.save(searchUnit)
    }

    override fun deleteSearchUnitById(id: Long) {
        checkExistsById(id)
        repository.deleteById(id)
    }

    override fun update(protocol: PublicProtocol) {
        checkExistsById(protocol.id)
        val context = createContext(protocol)
        val searchUnit = factory.create(protocol, context)
        repository.save(searchUnit)
    }

    private fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw ApproachSearchUnitNotFoundException("Protocol search unit not found with id: $id")
        }
    }

    private fun createContext(protocol: PublicProtocol): List<String> {
        return approachSearchUnitService.getContext(protocol.approach.id)
    }
}
