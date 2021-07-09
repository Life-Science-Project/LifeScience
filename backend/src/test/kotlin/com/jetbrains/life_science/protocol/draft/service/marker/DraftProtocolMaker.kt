package com.jetbrains.life_science.protocol.draft.service.marker

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.protocol.service.DraftProtocolInfo
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
