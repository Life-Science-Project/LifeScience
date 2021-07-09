package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftProtocolService {
    // DraftProtocolNotFoundException
    fun get(draftProtocolId: Long): DraftProtocol

    fun create(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftProtocolNotFoundException
    fun update(protocolInfo: DraftProtocolInfo): DraftProtocol

    // DraftProtocolNotFoundException
    fun addParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    // DraftProtocolNotFoundException
    // RemoveOwnerFromParticipantsException -- попытались удалить owner из participants
    fun removeParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    // DraftProtocolNotFoundException
    fun delete(draftProtocolId: Long)
}
