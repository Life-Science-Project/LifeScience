package com.jetbrains.life_science.controller.protocol.view

import com.jetbrains.life_science.container.protocol.entity.Protocol
import com.jetbrains.life_science.controller.approach.view.ApproachViewMapper
import org.springframework.stereotype.Component

@Component
class ProtocolViewMapper(
    val approachViewMapper: ApproachViewMapper,
) {
    fun toViewShort(protocol: Protocol): ProtocolShortView {
        return ProtocolShortView(
            id = protocol.id,
            name = protocol.name
        )
    }

    fun toView(protocol: Protocol): ProtocolView {
        return ProtocolView(
            id = protocol.id,
            name = protocol.name,
            approach = approachViewMapper.toViewShort(protocol.approach)
        )
    }

    fun toViewsShort(protocols: List<Protocol>): List<ProtocolShortView> {
        return protocols.map { toViewShort(it) }
    }

    fun toViews(protocols: List<Protocol>): List<ProtocolView> {
        return protocols.map { toView(it) }
    }
}
