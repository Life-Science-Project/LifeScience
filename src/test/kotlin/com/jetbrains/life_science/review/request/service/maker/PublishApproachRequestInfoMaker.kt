package com.jetbrains.life_science.review.request.service.maker

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

fun makePublishApproachRequestInfo(
    id: Long,
    date: LocalDateTime,
    editor: Credentials,
    approach: DraftApproach
): PublishApproachRequestInfo = object : PublishApproachRequestInfo {
    override val id = id
    override val date = date
    override val editor = editor
    override val approach = approach
}
