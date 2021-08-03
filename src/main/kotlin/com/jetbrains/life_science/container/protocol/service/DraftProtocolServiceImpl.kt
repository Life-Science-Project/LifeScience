package com.jetbrains.life_science.container.protocol.service

import com.jetbrains.life_science.exception.not_found.DraftProtocolNotFoundException
import com.jetbrains.life_science.exception.request.RemoveOwnerFromParticipantsException
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.factory.DraftProtocolFactory
import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
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
            DraftProtocolNotFoundException("Draft protocol with id $draftProtocolId is not found")
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

    override fun delete(draftProtocolId: Long) {
        if (!repository.existsById(draftProtocolId)) {
            throw DraftProtocolNotFoundException("Draft protocol with id $draftProtocolId is not found")
        }
        repository.deleteById(draftProtocolId)
    }

    override fun addSection(draftProtocolId: Long, section: Section): DraftProtocol {
        val draftProtocol = get(draftProtocolId)
        if (!draftProtocol.sections.any { it.id == section.id }) {
            draftProtocol.sections.add(section)
            repository.save(draftProtocol)
        }
        return draftProtocol
    }

    override fun removeSection(draftProtocolId: Long, section: Section): DraftProtocol {
        val draftProtocol = get(draftProtocolId)
        draftProtocol.sections.removeAll { it.id == section.id }
        repository.save(draftProtocol)
        return draftProtocol
    }

    override fun addParameter(draftProtocolId: Long, parameter: ProtocolParameter): DraftProtocol {
        val protocol = get(draftProtocolId)
        if (!protocol.parameters.any { it.id == parameter.id }) {
            protocol.parameters.add(parameter)
            repository.save(protocol)
        }
        return protocol
    }

    override fun removeParameter(draftProtocolId: Long, parameter: ProtocolParameter): DraftProtocol {
        val protocol = get(draftProtocolId)
        protocol.parameters.removeAll { it.id == parameter.id }
        return repository.save(protocol)
    }
}
