package com.jetbrains.life_science.container.protocol.service

import com.jetbrains.life_science.exception.not_found.ProtocolNotFoundException
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.container.protocol.factory.PublicProtocolFactory
import com.jetbrains.life_science.container.protocol.repository.PublicProtocolRepository
import com.jetbrains.life_science.container.protocol.search.service.ProtocolSearchUnitService
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Service

@Service
class PublicProtocolServiceImpl(
    val repository: PublicProtocolRepository,
    val factory: PublicProtocolFactory,
    val searchUnitService: ProtocolSearchUnitService
) : PublicProtocolService {
    override fun get(id: Long): PublicProtocol {
        return repository.findById(id).orElseThrow {
            ProtocolNotFoundException("Public protocol with id $id is not found")
        }
    }

    override fun getAllByOwnerId(ownerId: Long): List<PublicProtocol> {
        return repository.getAllByOwnerId(ownerId)
    }

    override fun create(protocol: DraftProtocol): PublicProtocol {
        val publicProtocol = factory.create(protocol)
        val savedPublicProtocol = repository.save(publicProtocol)
        searchUnitService.createSearchUnit(savedPublicProtocol)
        return savedPublicProtocol
    }

    override fun hasSection(id: Long, section: Section): Boolean {
        if (!repository.existsById(id)) {
            throw ProtocolNotFoundException("Public protocol with id $id is not found")
        }
        return repository.existsByIdAndSectionsContains(id, section)
    }

    override fun addSection(id: Long, section: Section) {
        if (!hasSection(id, section)) {
            val publicProtocol = get(id)
            publicProtocol.sections.add(section)
            repository.save(publicProtocol)
        }
    }

    override fun removeSection(id: Long, section: Section) {
        if (hasSection(id, section)) {
            val publicProtocol = get(id)
            publicProtocol.sections.removeAll { it.id == section.id }
            repository.save(publicProtocol)
        }
    }

    override fun isInApproach(protocolId: Long, approachId: Long): Boolean {
        return repository.existsByIdAndApproachId(protocolId, approachId)
    }
}
