package com.jetbrains.life_science.container.protocol.service

import com.jetbrains.life_science.container.ContainsSections
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section

interface PublicProtocolService : ContainsSections {
    fun get(id: Long): PublicProtocol

    fun getAllByOwnerId(ownerId: Long): List<PublicProtocol>

    fun create(protocol: DraftProtocol): PublicProtocol

    override fun addSection(id: Long, section: Section)

    override fun removeSection(id: Long, section: Section)

    fun isInApproach(protocolId: Long, approachId: Long): Boolean
}
