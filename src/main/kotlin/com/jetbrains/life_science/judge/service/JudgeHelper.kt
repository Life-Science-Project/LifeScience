package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.judge.events.JudgeDecisionApproveEvent
import com.jetbrains.life_science.judge.events.JudgeDecisionRejectEvent
import com.jetbrains.life_science.review.request.entity.ReviewRequest
import com.jetbrains.life_science.review.request.service.JudgeExecutorService
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class JudgeHelper(
    val applicationEventPublisher: ApplicationEventPublisher
) {
    @Value("\${verdictLimit}")
    val verdictLimit = 10

    @Value("\${approveLimit}")
    val approveLimit = 7

    fun approveRequest(request: ReviewRequest, service: JudgeExecutorService, event: JudgeDecisionApproveEvent) {
        // Delete publish request
        service.delete(request.id)
        // Publish event
        applicationEventPublisher.publishEvent(event)
    }

    fun cancelRequest(request: ReviewRequest, service: JudgeExecutorService, event: JudgeDecisionRejectEvent) {
        // Reject request
        service.cancel(request.id)
        // Publish event
        applicationEventPublisher.publishEvent(event)
    }

    fun canBeApproved(request: ReviewRequest) =
        request.reviews.count { it.resolution == ReviewResolution.APPROVE } >= approveLimit

    fun canBeJudged(request: ReviewRequest) = request.reviews.size == verdictLimit
}
