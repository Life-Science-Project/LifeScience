package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol

interface PublisherService {
    fun publishDraftApproach(draftApproach: DraftApproach): PublicApproach

    fun publishDraftProtocol(draftProtocol: DraftProtocol): PublicProtocol

    fun publishApproachEditRecord(approachEditRecord: ApproachEditRecord): PublicApproach

    fun publishProtocolEditRecord(protocolEditRecord: ProtocolEditRecord): PublicProtocol
}
