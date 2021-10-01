package com.jetbrains.life_science.review.request.service.publish

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.time.LocalDateTime

interface PublishApproachRequestInfo {
    val id: Long

    val date: LocalDateTime

    val editor: Credentials

    val approach: DraftApproach
}
