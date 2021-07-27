package com.jetbrains.life_science.container.protocol.search.factory

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.container.protocol.search.ProtocolSearchUnit
import org.springframework.stereotype.Component

@Component
class ProtocolSearchUnitFactory {

    fun create(protocol: PublicProtocol, context: List<String>): ProtocolSearchUnit {
        return ProtocolSearchUnit(protocol.id, protocol.approach.id, listOf(protocol.name), context)
    }
}
