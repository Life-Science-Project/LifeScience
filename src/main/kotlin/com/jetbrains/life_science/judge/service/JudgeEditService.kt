package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest

interface JudgeEditService {

    fun judgeApproachEditRecord(request: ApproachReviewRequest)

    fun judgeProtocolEditRecord(request: ProtocolReviewRequest)
}
