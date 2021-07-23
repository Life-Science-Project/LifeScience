package com.jetbrains.life_science.protocol.service

import com.jetbrains.life_science.util.interfaces.ContainsSections
import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section

interface PublicProtocolService : ContainsSections {
    fun get(id: Long): PublicProtocol

    fun create(protocol: DraftProtocol): PublicProtocol

    override fun addSection(id: Long, section: Section)

    override fun removeSection(id: Long, section: Section)
}
