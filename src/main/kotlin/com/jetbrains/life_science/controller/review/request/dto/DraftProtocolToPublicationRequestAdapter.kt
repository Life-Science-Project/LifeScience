package com.jetbrains.life_science.controller.review.request.dto

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.util.UTCZone
import java.time.LocalDateTime

class DraftProtocolToPublicationRequestAdapter(
    override val editor: Credentials,
    override val protocol: DraftProtocol
) : PublishProtocolRequestInfo {
    override val id: Long = 0
    override val date: LocalDateTime = LocalDateTime.now(UTCZone)
}
