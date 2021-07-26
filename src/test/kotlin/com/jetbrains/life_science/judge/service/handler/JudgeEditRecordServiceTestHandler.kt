package com.jetbrains.life_science.judge.service.handler

import com.jetbrains.life_science.judge.events.JudgeEditRecordApproachApproveEvent
import com.jetbrains.life_science.judge.events.JudgeEditRecordApproachRejectEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class JudgeEditRecordServiceTestHandler {
    @EventListener
    fun listenApproachReject(event: JudgeEditRecordApproachRejectEvent) {
    }

    @EventListener
    fun listenApproachApprove(event: JudgeEditRecordApproachApproveEvent) {
    }

    // TODO
    // @EventListener
    // fun listenProtocolReject(event: JudgeEditRecordProtocolRejectEvent) {
    // }
    //
    // @EventListener
    // fun listenProtocolApprove(event: JudgeEditRecordProtocolApproveEvent) {
    // }
}
