package com.jetbrains.life_science.protocol.published.service.maker

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.service.PublicProtocolInfo

fun makePublicProtocolInfo(
    draftProtocol: DraftProtocol
) = object : PublicProtocolInfo {
    override val protocol = draftProtocol
}
