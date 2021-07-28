package com.jetbrains.life_science.review.request.service.maker

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

fun makePublishProtocolRequestInfo(
    id: Long,
    date: LocalDateTime,
    editor: Credentials,
    protocol: DraftProtocol
): PublishProtocolRequestInfo = object : PublishProtocolRequestInfo {
    override val id = id
    override val date = date
    override val editor = editor
    override val protocol = protocol
}
