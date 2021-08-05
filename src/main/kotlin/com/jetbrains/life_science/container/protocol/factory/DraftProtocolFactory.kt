package com.jetbrains.life_science.container.protocol.factory

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.service.DraftProtocolInfo
import org.springframework.stereotype.Component

@Component
class DraftProtocolFactory {
    fun create(info: DraftProtocolInfo): DraftProtocol {
        return DraftProtocol(
            id = 0,
            name = info.name,
            approach = info.approach,
            owner = info.owner,
            sections = mutableListOf(),
            participants = mutableListOf(info.owner)
        )
    }

    fun setParams(draftProtocol: DraftProtocol, info: DraftProtocolInfo) {
        draftProtocol.name = info.name
        draftProtocol.approach = info.approach
        draftProtocol.participants = mutableListOf(info.owner)
    }
}
