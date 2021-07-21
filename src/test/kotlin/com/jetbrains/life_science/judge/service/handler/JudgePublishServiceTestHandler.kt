package com.jetbrains.life_science.judge.service.handler

import com.jetbrains.life_science.judge.events.JudgePublishApproachApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishApproachRejectEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolRejectEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class JudgePublishServiceTestHandler {
    @EventListener
    fun listenApproachReject(event: JudgePublishApproachRejectEvent) {
    }

    @EventListener
    fun listenApproachApprove(event: JudgePublishApproachApproveEvent) {
    }

    @EventListener
    fun listenProtocolReject(event: JudgePublishProtocolRejectEvent) {
    }

    @EventListener
    fun listenProtocolApprove(event: JudgePublishProtocolApproveEvent) {
    }
}
