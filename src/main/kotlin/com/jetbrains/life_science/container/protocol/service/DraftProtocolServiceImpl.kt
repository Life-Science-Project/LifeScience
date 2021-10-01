package com.jetbrains.life_science.container.protocol.service

import com.jetbrains.life_science.exception.not_found.ProtocolNotFoundException
import com.jetbrains.life_science.exception.request.RemoveOwnerFromParticipantsException
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.factory.DraftProtocolFactory
import com.jetbrains.life_science.container.protocol.repository.DraftProtocolRepository
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.stereotype.Service

@Service
class DraftProtocolServiceImpl(
    val repository: DraftProtocolRepository,
    val factory: DraftProtocolFactory
) : DraftProtocolService {
    override fun get(draftProtocolId: Long): DraftProtocol {
        return repository.findById(draftProtocolId).orElseThrow {
            ProtocolNotFoundException("Draft protocol with id $draftProtocolId is not found")
        }
    }

    override fun create(protocolInfo: DraftProtocolInfo): DraftProtocol {
        val draftProtocol = factory.create(protocolInfo)
        return repository.save(draftProtocol)
    }

    override fun update(protocolInfo: DraftProtocolInfo): DraftProtocol {
        val protocol = get(protocolInfo.id)
        factory.setParams(protocol, protocolInfo)
        return repository.save(protocol)
    }

    override fun addParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol {
        val protocol = get(draftProtocolId)
        if (!protocol.participants.any { it.id == credentials.id }) {
            protocol.participants.add(credentials)
            repository.save(protocol)
        }
        return protocol
    }

    override fun removeParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol {
        val protocol = get(draftProtocolId)
        if (protocol.owner.id == credentials.id) {
            throw RemoveOwnerFromParticipantsException("Can't remove owner from protocol participants")
        }
        protocol.participants.removeAll { it.id == credentials.id }
        return repository.save(protocol)
    }

    override fun hasParticipant(draftProtocolId: Long, user: Credentials): Boolean {
        exists(draftProtocolId)
        return repository.existsByIdAndParticipantsContains(draftProtocolId, user)
    }

    override fun delete(draftProtocolId: Long) {
        exists(draftProtocolId)
        repository.deleteById(draftProtocolId)
    }

    override fun addSection(id: Long, section: Section) {
        val draftProtocol = get(id)
        if (!draftProtocol.sections.any { it.id == section.id }) {
            draftProtocol.sections.add(section)
            repository.save(draftProtocol)
        }
    }

    override fun removeSection(id: Long, section: Section) {
        val draftProtocol = get(id)
        draftProtocol.sections.removeAll { it.id == section.id }
        repository.save(draftProtocol)
    }

    override fun hasSection(id: Long, section: Section): Boolean {
        exists(id)
        return repository.existsByIdAndSectionsContains(id, section)
    }

    private fun exists(draftApproachId: Long) {
        if (!repository.existsById(draftApproachId)) {
            throw ProtocolNotFoundException("Draft approach with id $draftApproachId is not found")
        }
    }

    override fun getAllByOwnerId(ownerId: Long): List<DraftProtocol> {
        return repository.getAllByOwnerId(ownerId)
    }
}
