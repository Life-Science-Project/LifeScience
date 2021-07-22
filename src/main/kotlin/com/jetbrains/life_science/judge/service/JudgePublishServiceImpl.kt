package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.exception.judge.RequestJudgeWrongStateException
import com.jetbrains.life_science.judge.events.JudgePublishApproachApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishApproachRejectEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolRejectEvent
import com.jetbrains.life_science.publisher.service.PublisherService
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestService
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class JudgePublishServiceImpl(
    val publisher: PublisherService,
    val publishApproachRequestService: PublishApproachRequestService,
    val publishProtocolRequestService: PublishProtocolRequestService,
    val helper: JudgeHelper
) : JudgePublishService {

    override fun judgeApproachPublish(request: PublishApproachRequest) {
        if (request.state == RequestState.CANCELED) {
            throw RequestJudgeWrongStateException("Public approach request ${request.id} has wrong state to be judged")
        }
        if (!helper.canBeJudged(request)) {
            return
        }

        if (helper.canBeApproved(request)) {
            val publicApproach = publisher.publishDraftApproach(request.approach)
            helper.approveRequest(
                request,
                publishApproachRequestService,
                JudgePublishApproachApproveEvent(publicApproach.id)
            )
        } else {
            helper.cancelRequest(
                request,
                publishApproachRequestService,
                JudgePublishApproachRejectEvent(request.id)
            )
        }
    }

    override fun judgeProtocolPublish(request: PublishProtocolRequest) {
        if (request.state == RequestState.CANCELED) {
            throw RequestJudgeWrongStateException("Public protocol request ${request.id} has wrong state to be judged")
        }
        if (!helper.canBeJudged(request)) {
            return
        }

        if (helper.canBeApproved(request)) {
            val publicProtocol = publisher.publishDraftProtocol(request.protocol)
            helper.approveRequest(
                request,
                publishProtocolRequestService,
                JudgePublishProtocolApproveEvent(publicProtocol.id)
            )
        } else {
            helper.cancelRequest(
                request,
                publishProtocolRequestService,
                JudgePublishProtocolRejectEvent(request.id)
            )
        }
    }
}
