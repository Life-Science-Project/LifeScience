package com.jetbrains.life_science.judge.events

import org.springframework.context.ApplicationEvent

class JudgePublishApproachApproveEvent(
    source: Any,
    val requestId: Long
) : ApplicationEvent(source)
