package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.exception.judge.RequestJudgeWrongStateException
import com.jetbrains.life_science.judge.events.JudgeEditRecordApproachApproveEvent
import com.jetbrains.life_science.judge.events.JudgeEditRecordApproachRejectEvent
import com.jetbrains.life_science.judge.events.JudgeEditRecordProtocolApproveEvent
import com.jetbrains.life_science.judge.events.JudgeEditRecordProtocolRejectEvent
import com.jetbrains.life_science.publisher.service.PublisherService
import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.editRecord.ApproachReviewRequestService
import com.jetbrains.life_science.review.request.service.editRecord.ProtocolReviewRequestService
import org.springframework.stereotype.Service

@Service
class JudgeEditServiceImpl(
    val publisher: PublisherService,
    val approachReviewRequestService: ApproachReviewRequestService,
    val protocolReviewRequestService: ProtocolReviewRequestService,
    val helper: JudgeHelper
) : JudgeEditService {

    override fun judgeApproachEditRecord(request: ApproachReviewRequest) {
        if (request.state == RequestState.CANCELED) {
            throw RequestJudgeWrongStateException("Approach review request ${request.id} has wrong state to be judged")
        }
        if (!helper.canBeJudged(request)) {
            return
        }

        if (helper.canBeApproved(request)) {
            val publicApproach = publisher.publishApproachEditRecord(request.editRecord)
            helper.approveRequest(
                request,
                approachReviewRequestService,
                JudgeEditRecordApproachApproveEvent(publicApproach.id)
            )
        } else {
            helper.cancelRequest(request, approachReviewRequestService, JudgeEditRecordApproachRejectEvent(request.id))
        }
    }

    override fun judgeProtocolEditRecord(request: ProtocolReviewRequest) {
        if (request.state == RequestState.CANCELED) {
            throw RequestJudgeWrongStateException("Protocol review request ${request.id} has wrong state to be judged")
        }
        if (!helper.canBeJudged(request)) {
            return
        }

        if (helper.canBeApproved(request)) {
            val publicProtocol = publisher.publishProtocolEditRecord(request.editRecord)
            helper.approveRequest(
                request,
                protocolReviewRequestService,
                JudgeEditRecordProtocolApproveEvent(publicProtocol.id)
            )
        } else {
            helper.cancelRequest(request, protocolReviewRequestService, JudgeEditRecordProtocolRejectEvent(request.id))
        }
    }
}
