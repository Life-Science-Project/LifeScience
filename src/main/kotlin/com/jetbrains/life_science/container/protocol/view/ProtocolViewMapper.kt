package com.jetbrains.life_science.container.protocol.view

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import org.springframework.stereotype.Component

@Component
class ProtocolViewMapper {
    fun toViewShort(protocol: PublicProtocol): ProtocolShortView {
        return ProtocolShortView(
            name = protocol.name,
            id = protocol.id
        )
    }

    fun toViewsShort(protocols: List<PublicProtocol>): List<ProtocolShortView> {
        return protocols.map { toViewShort(it) }
    }
}
