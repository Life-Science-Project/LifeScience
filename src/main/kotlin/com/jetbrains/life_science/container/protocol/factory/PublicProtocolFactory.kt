package com.jetbrains.life_science.container.protocol.factory

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import org.springframework.stereotype.Component

@Component
class PublicProtocolFactory {
    fun create(protocol: DraftProtocol): PublicProtocol {
        return PublicProtocol(
            id = 0,
            name = protocol.name,
            sections = protocol.sections.toMutableList(),
            owner = protocol.owner,
            approach = protocol.approach,
            coAuthors = protocol.participants.toMutableList(),
            rating = 0L
        )
    }
}
