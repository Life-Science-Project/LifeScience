package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.approach.service.PublicApproachInfo
import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.judge.events.JudgePublishApproachApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishApproachRejectEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolApproveEvent
import com.jetbrains.life_science.judge.events.JudgePublishProtocolRejectEvent
import com.jetbrains.life_science.protocol.service.DraftProtocolService
import com.jetbrains.life_science.protocol.service.PublicProtocolInfo
import com.jetbrains.life_science.protocol.service.PublicProtocolService
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.ReviewRequest
import com.jetbrains.life_science.review.request.service.PublishApproachRequestService
import com.jetbrains.life_science.review.request.service.PublishProtocolRequestService
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import com.jetbrains.life_science.section.entity.Section
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
    val publicProtocolService: PublicProtocolService,
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

            // Create public entity
            val publicApproach = publicApproachService.create(
                object : PublicApproachInfo {
                    override val approach = draftApproach
                }
            )

            // Delete draft entity
            draftApproachService.delete(draftApproach.id)

            // Publish sections in public entity
            publishSections(publicApproach.sections)

            // Delete publish request
            publishApproachRequestService.delete(request.id)

            // Publish event
            applicationEventPublisher.publishEvent(
                JudgePublishApproachApproveEvent(this, publicApproach.id)
            )
        } else {
            // Reject request
            publishApproachRequestService.cancel(request.id)

            // Publish event
            applicationEventPublisher.publishEvent(
                JudgePublishApproachRejectEvent(this, request.id)
            )
        }
    }

    override fun judgeProtocolPublish(request: PublishProtocolRequest) {
        if (!canBeJudged(request)) {
            return
        }

        if (canBeApproved(request)) {
            val draftProtocol = request.protocol

            // Create public entity
            val publicProtocol = publicProtocolService.create(
                object : PublicProtocolInfo {
                    override val protocol = draftProtocol
                }
            )

            // Delete draft entity
            draftProtocolService.delete(draftProtocol.id)

            // Publish sections in public entity
            publishSections(publicProtocol.sections)

            // Delete publish request
            publishProtocolRequestService.delete(request.id)

            // Publish event
            applicationEventPublisher.publishEvent(
                JudgePublishProtocolApproveEvent(this, publicProtocol.id)
            )
        } else {
            // Reject request
            publishProtocolRequestService.cancel(request.id)

            // Publish event
            applicationEventPublisher.publishEvent(
                JudgePublishProtocolRejectEvent(this, request.id)
            )
        }
    }

    private fun canBeApproved(request: ReviewRequest) =
        request.reviews.count { it.resolution == ReviewResolution.APPROVE } == approveLimit

    private fun canBeJudged(request: ReviewRequest) = request.reviews.size != verdictLimit

    private fun publishSections(sections: List<Section>) = sections.forEach { sectionService.publish(it.id) }
}
