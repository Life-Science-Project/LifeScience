package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.approach.service.PublicApproachInfo
import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.judge.events.JudgePublishApproachApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishApproachRejectEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolRejectEvent
import com.jetbrains.life_science.protocol.service.DraftProtocolService
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.ReviewRequest
import com.jetbrains.life_science.review.request.service.PublishApproachRequestService
import com.jetbrains.life_science.review.request.service.PublishProtocolRequestService
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import com.jetbrains.life_science.section.service.SectionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class JudgePublishServiceImpl(
    val sectionService: SectionService,
    val publishApproachRequestService: PublishApproachRequestService,
    val publishProtocolRequestService: PublishProtocolRequestService,
    val draftApproachService: DraftApproachService,
    val publicApproachService: PublicApproachService,
    val draftProtocolService: DraftProtocolService,
    //val publicProtocolService: PublicProtocolService,
    val applicationEventPublisher: ApplicationEventPublisher
) : JudgePublishService {

    @Value("\${verdictLimit}")
    override val verdictLimit = 10

    @Value("\${approveLimit}")
    override val approveLimit = 10

    override fun judgeApproachPublish(request: PublishApproachRequest) {
        if (!canBeJudged(request)) {
            return
        }

        if (canBeApproved(request)) {
            val draftApproach = request.approach
            val publicApproach = publicApproachService.create(
                object : PublicApproachInfo {
                    override val approach = draftApproach
                }
            )
            draftApproachService.delete(draftApproach.id)
            publicApproach.sections.forEach {
                sectionService.publish(it.id)
            }
            publishApproachRequestService.approve(request.id)
            applicationEventPublisher.publishEvent(
                JudgePublishApproachApproveEvent(
                    this,
                    request.id
                )
            )
        } else {
            publishApproachRequestService.cancel(request.id)
            applicationEventPublisher.publishEvent(
                JudgePublishApproachRejectEvent(
                    this,
                    request.id
                )
            )
        }
    }

    override fun judgeProtocolPublish(request: PublishProtocolRequest) {
        if (!canBeJudged(request)) {
            return
        }

        if (canBeApproved(request)) {
            TODO("implement PublicProtoService first")
        } else {
            publishProtocolRequestService.cancel(request.id)
            applicationEventPublisher.publishEvent(
                JudgePublishProtocolRejectEvent(
                    this,
                    request.id
                )
            )
        }
    }

    private fun canBeApproved(request: ReviewRequest) =
        request.reviews.count { it.resolution == ReviewResolution.APPROVE } == approveLimit

    private fun canBeJudged(request: ReviewRequest) = request.reviews.size != verdictLimit
}
