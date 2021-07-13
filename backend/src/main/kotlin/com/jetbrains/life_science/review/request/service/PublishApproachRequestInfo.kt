package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.util.*

interface PublishApproachRequestInfo {
    val id: Long

    val date: Date

    val state: RequestState

    val editor: Credentials

    val approach: DraftApproach
}
