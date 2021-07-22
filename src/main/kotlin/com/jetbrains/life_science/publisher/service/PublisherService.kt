package com.jetbrains.life_science.publisher.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.protocol.entity.DraftProtocol
import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.review.edit_record.entity.ApproachEditRecord

interface PublisherService {
    fun publishDraftApproach(draftApproach: DraftApproach): PublicApproach

    fun publishDraftProtocol(draftProtocol: DraftProtocol): PublicProtocol

    fun publishApproachEditRecord(approachEditRecord: ApproachEditRecord): PublicApproach

    fun publishProtocolEditRecord(protocolEditRecord: ApproachEditRecord): PublicProtocol
}
