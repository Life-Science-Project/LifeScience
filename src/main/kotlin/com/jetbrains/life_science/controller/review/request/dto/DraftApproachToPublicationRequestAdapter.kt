package com.jetbrains.life_science.controller.review.request.dto

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.util.UTCZone
import java.time.LocalDateTime

class DraftApproachToPublicationRequestAdapter(
    override val editor: Credentials,
    override val approach: DraftApproach
) : PublishApproachRequestInfo {
    override val id: Long = 0
    override val date: LocalDateTime = LocalDateTime.now(UTCZone)
}
