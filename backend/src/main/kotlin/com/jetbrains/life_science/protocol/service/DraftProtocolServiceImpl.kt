package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.exception.not_found.DraftProtocolNotFoundException
import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.factory.DraftProtocolFactory
import com.jetbrains.life_science.protocol.repository.DraftProtocolRepository
import com.jetbrains.life_science.user.data.entity.UserPersonalData
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

    override fun addParticipant(draftProtocolId: Long, userPersonalData: UserPersonalData) {
        val protocol = get(draftProtocolId)
        if (!protocol.participants.any { it.id == userPersonalData.id }) {
            protocol.participants.add(userPersonalData)
            repository.save(protocol)
        }
    }

    override fun removeParticipant(draftProtocolId: Long, userPersonalData: UserPersonalData) {
        val protocol = get(draftProtocolId)
        protocol.participants.removeAll { it.id == userPersonalData.id }
        repository.save(protocol)
    }

    override fun delete(draftProtocolId: Long) {
        get(draftProtocolId)
        repository.deleteById(draftProtocolId)
    }
}
