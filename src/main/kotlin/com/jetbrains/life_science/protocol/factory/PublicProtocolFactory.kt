package com.jetbrains.life_science.protocol.factory

import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.protocol.service.PublicProtocolInfo
import org.springframework.stereotype.Component

@Component
class PublicProtocolFactory {
    fun create(info: PublicProtocolInfo): PublicProtocol {
        return PublicProtocol(
            id = 0,
            name = info.protocol.name,
            sections = info.protocol.sections.toMutableList(),
            owner = info.protocol.owner,
            approach = info.protocol.approach,
            coAuthors = info.protocol.participants.toMutableList(),
            rating = 0L
        )
    }
}
