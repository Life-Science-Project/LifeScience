package com.jetbrains.life_science.judge.events

class JudgePublishProtocolApproveEvent(
    publishedProtocolId: Long
) : JudgeDecisionApproveEvent(publishedProtocolId)
