package com.jetbrains.life_science.container.protocol.service

import com.jetbrains.life_science.container.ContainsSections
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftProtocolService : ContainsSections {

    fun get(draftProtocolId: Long): DraftProtocol

    fun create(protocolInfo: DraftProtocolInfo): DraftProtocol

    fun update(protocolInfo: DraftProtocolInfo): DraftProtocol

    fun delete(draftProtocolId: Long)

    fun addParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    fun removeParticipant(draftProtocolId: Long, credentials: Credentials): DraftProtocol

    fun hasParticipant(draftProtocolId: Long, user: Credentials): Boolean

    override fun addSection(id: Long, section: Section)

    override fun removeSection(id: Long, section: Section)

    fun getAllByOwnerId(ownerId: Long): List<DraftProtocol>
}
