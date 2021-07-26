package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest

interface JudgePublishService {
    fun judgeApproachPublish(request: PublishApproachRequest)

    fun judgeProtocolPublish(request: PublishProtocolRequest)
}
