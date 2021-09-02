package com.jetbrains.life_science.controller.protocol.view

import com.jetbrains.life_science.container.protocol.entity.Protocol
import org.springframework.stereotype.Component

@Component
class ProtocolViewMapper {
    fun toViewShort(protocol: Protocol): ProtocolShortView {
        return ProtocolShortView(
            name = protocol.name,
            id = protocol.id
        )
    }

    fun toViewsShort(protocols: List<Protocol>): List<ProtocolShortView> {
        return protocols.map { toViewShort(it) }
    }
}
