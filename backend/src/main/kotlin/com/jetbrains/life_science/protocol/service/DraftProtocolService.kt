package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftProtocolService {
    // DraftProtocolNotFoundException
    fun get(draftProtocolId: Long): DraftProtocol

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    fun create(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    // DraftProtocolNotFoundException
    fun update(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftProtocolNotFoundException
    fun addParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    // DraftProtocolNotFoundException
    fun removeParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    // DraftProtocolNotFoundException
    fun delete(draftProtocolId: Long)
}
