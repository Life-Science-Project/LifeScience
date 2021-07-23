package com.jetbrains.life_science.review.request.service.publish

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

interface PublishProtocolRequestInfo {
    val id: Long

    val date: LocalDateTime

    val editor: Credentials

    val protocol: DraftProtocol
}
