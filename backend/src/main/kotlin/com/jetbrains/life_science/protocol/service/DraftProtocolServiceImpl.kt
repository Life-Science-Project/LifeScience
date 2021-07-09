package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.exception.not_found.DraftProtocolNotFoundException
import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.factory.DraftProtocolFactory
import com.jetbrains.life_science.protocol.repository.DraftProtocolRepository
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

    // TODO:: проверка что approach существует
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
        protocol.participants.removeAll { it.id == credentials.id }
        return repository.save(protocol)
    }

    override fun delete(draftProtocolId: Long) {
        get(draftProtocolId)
        repository.deleteById(draftProtocolId)
    }
}