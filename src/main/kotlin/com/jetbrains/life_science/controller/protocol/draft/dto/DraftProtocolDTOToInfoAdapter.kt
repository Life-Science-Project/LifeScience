package com.jetbrains.life_science.controller.protocol.draft.dto

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.protocol.service.DraftProtocolInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials

class DraftProtocolDTOToInfoAdapter(
    dto: DraftProtocolDTO,
    parentApproach: PublicApproach,
    override val owner: Credentials
) : DraftProtocolInfo {
    override val id: Long = 0
    override val name = dto.name
    override val approach = parentApproach
}
