package com.jetbrains.life_science.protocol.search.factory

import com.jetbrains.life_science.protocol.entity.Protocol
import com.jetbrains.life_science.protocol.search.ProtocolSearchUnit
import org.springframework.stereotype.Component

@Component
class ProtocolSearchUnitFactory {

    fun create(protocol: Protocol): ProtocolSearchUnit {
        return ProtocolSearchUnit(protocol.id, protocol.name)
    }
}
