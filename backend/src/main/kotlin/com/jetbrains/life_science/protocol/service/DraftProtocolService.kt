package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftProtocolService {
    // DraftProtocolNotExistException - если не существует
    fun get(draftProtocolId: Long): DraftProtocol

    fun create(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    fun update(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    fun addParticipant(draftProtocolId: Long, credentials: Credentials)

    // DraftApproachNotExistException - не найден approach, к которому прикрепить протокол
    fun removeParticipant(draftProtocolId: Long, credentials: Credentials)

    // DraftProtocolNotExistException - если не существует
    fun delete(draftProtocolId: Long)
}
