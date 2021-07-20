package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.entity.PublicProtocol

interface PublisherService {
    fun publishDraftApproach(draftApproach: DraftApproach): PublicApproach

    fun publishDraftProtocol(draftProtocol: DraftProtocol): PublicProtocol

    fun publishApproachEditRecord()

    fun publishProtocolEditRecord()
}
