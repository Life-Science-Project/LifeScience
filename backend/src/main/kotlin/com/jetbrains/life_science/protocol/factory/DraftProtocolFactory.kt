package com.jetbrains.life_science.protocol.factory

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.service.DraftProtocolInfo
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
            participants = mutableListOf()
        )
    }

    fun setParams(draftProtocol: DraftProtocol, info: DraftProtocolInfo) {
        draftProtocol.name = info.name
        draftProtocol.owner = info.owner
        draftProtocol.approach = info.approach
    }
}
