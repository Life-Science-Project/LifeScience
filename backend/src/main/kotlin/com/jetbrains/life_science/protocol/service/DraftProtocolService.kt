package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftProtocolService {
    // DraftProtocolNotFoundException
    fun get(draftProtocolId: Long): DraftProtocol

    // PublicApproachNotFoundException - не найден approach, к которому прикрепить протокол
    // UserNotFoundException - не найден owner протокола
    fun create(protocolInfo: DraftProtocolInfo): DraftProtocol

    // PublicApproachNotFoundException - не найден approach, к которому прикрепить протокол
    // DraftProtocolNotFoundException
    // UserNotFoundException - не найден owner протокола
    fun update(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftProtocolNotFoundException
    fun addParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    // DraftProtocolNotFoundException
    // TODO:: исключение на удаление owner из participants
    fun removeParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    // DraftProtocolNotFoundException
    fun delete(draftProtocolId: Long)
}
