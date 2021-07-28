package com.jetbrains.life_science.container.protocol.draft.service.maker

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.protocol.service.DraftProtocolInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials

fun makeDraftProtocolInfo(
    id: Long,
    name: String,
    approach: PublicApproach,
    owner: Credentials
): DraftProtocolInfo = object : DraftProtocolInfo {
    override val id = id
    override val name = name
    override val approach = approach
    override val owner = owner
}
