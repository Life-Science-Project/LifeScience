package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.exception.judge.RequestJudgeWrongStateException
import com.jetbrains.life_science.judge.events.JudgeDecisionApproveEvent
import com.jetbrains.life_science.judge.events.JudgeDecisionRejectEvent
import com.jetbrains.life_science.judge.events.JudgePublishApproachApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishApproachRejectEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolRejectEvent
import com.jetbrains.life_science.publisher.service.PublisherService
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.entity.ReviewRequest
import com.jetbrains.life_science.review.request.service.JudgeExecutorService
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestService
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestService
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class JudgePublishServiceImpl(
    val publisher: PublisherService,
    val publishApproachRequestService: PublishApproachRequestService,
    val publishProtocolRequestService: PublishProtocolRequestService,
    val applicationEventPublisher: ApplicationEventPublisher
) : JudgePublishService {

    @Value("\${verdictLimit}")
    override val verdictLimit = 10

    @Value("\${approveLimit}")
    override val approveLimit = 7

    override fun judgeApproachPublish(request: PublishApproachRequest) {
        if (request.state == RequestState.CANCELED) {
            throw RequestJudgeWrongStateException("Public approach request ${request.id} has wrong state to be judged")
        }
        if (!canBeJudged(request)) {
            return
        }

        if (canBeApproved(request)) {
            val publicApproach = publisher.publishDraftApproach(request.approach)
            approveRequest(request, publishApproachRequestService, JudgePublishApproachApproveEvent(publicApproach.id))
        } else {
            cancelRequest(request, publishApproachRequestService, JudgePublishApproachRejectEvent(request.id))
        }
    }

    override fun judgeProtocolPublish(request: PublishProtocolRequest) {
        if (request.state == RequestState.CANCELED) {
            throw RequestJudgeWrongStateException("Public protocol request ${request.id} has wrong state to be judged")
        }
        if (!canBeJudged(request)) {
            return
        }

        if (canBeApproved(request)) {
            val publicProtocol = publisher.publishDraftProtocol(request.protocol)
            approveRequest(request, publishProtocolRequestService, JudgePublishProtocolApproveEvent(publicProtocol.id))
        } else {
            cancelRequest(request, publishProtocolRequestService, JudgePublishProtocolRejectEvent(request.id))
        }
    }

    private fun approveRequest(request: ReviewRequest, service: JudgeExecutorService, event: JudgeDecisionApproveEvent) {
        // Delete publish request
        service.delete(request.id)
        // Publish event
        applicationEventPublisher.publishEvent(event)
    }

    private fun cancelRequest(request: ReviewRequest, service: JudgeExecutorService, event: JudgeDecisionRejectEvent) {
        // Reject request
        service.cancel(request.id)
        // Publish event
        applicationEventPublisher.publishEvent(event)
    }

    private fun canBeApproved(request: ReviewRequest) =
        request.reviews.count { it.resolution == ReviewResolution.APPROVE } >= approveLimit

    private fun canBeJudged(request: ReviewRequest) = request.reviews.size == verdictLimit
}
