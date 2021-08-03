package com.jetbrains.life_science.container.protocol.service

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftProtocolService {

    fun get(draftProtocolId: Long): DraftProtocol

    fun create(protocolInfo: DraftProtocolInfo): DraftProtocol

    fun update(protocolInfo: DraftProtocolInfo): DraftProtocol

    fun addParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    fun removeParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    fun delete(draftProtocolId: Long)

    fun addSection(draftProtocolId: Long, section: Section): DraftProtocol

    fun removeSection(draftProtocolId: Long, section: Section): DraftProtocol

    fun addParameter(draftProtocolId: Long, parameter: ProtocolParameter): DraftProtocol

    fun removeParameter(draftProtocolId: Long, parameter: ProtocolParameter): DraftProtocol
}
