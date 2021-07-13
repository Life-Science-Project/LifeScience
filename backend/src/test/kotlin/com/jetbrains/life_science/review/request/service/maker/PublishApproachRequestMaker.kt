package com.jetbrains.life_science.review.request.service.maker

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.PublishApproachRequestInfo
import com.jetbrains.life_science.user.credentials.entity.Credentials
import java.util.*

fun makePublishApproachRequest(
    id: Long,
    date: Date,
    state: RequestState,
    editor: Credentials,
    approach: DraftApproach
): PublishApproachRequestInfo = object : PublishApproachRequestInfo {
    override val id = id
    override val date = date
    override val state = state
    override val editor = editor
    override val approach = approach
}
