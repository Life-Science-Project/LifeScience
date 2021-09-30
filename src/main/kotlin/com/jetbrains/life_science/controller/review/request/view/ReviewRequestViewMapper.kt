package com.jetbrains.life_science.controller.review.request.view

import com.jetbrains.life_science.controller.protocol.view.ProtocolViewMapper
import com.jetbrains.life_science.controller.approach.view.ApproachViewMapper
import com.jetbrains.life_science.controller.review.response.view.ReviewViewMapper
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import org.springframework.stereotype.Component

@Component
class ReviewRequestViewMapper(
    val reviewMapper: ReviewViewMapper,
    val userViewMapper: UserViewMapper,
    val approachViewMapper: ApproachViewMapper,
    val protocolViewMapper: ProtocolViewMapper
) {
    fun draftApproachRequestToView(request: PublishApproachRequest): ApproachReviewRequestView {
        val userView = request.editor.userPersonalData?.let {
            userViewMapper.toShortView(it)
        }
        return ApproachReviewRequestView(
            id = request.id,
            date = request.date,
            state = request.state,
            reviews = reviewMapper.toViewsAll(request.reviews),
            editor = userView,
            approach = approachViewMapper.toViewShort(request.approach)
        )
    }

    fun draftProtocolRequestToView(request: PublishProtocolRequest): ProtocolReviewRequestView {
        val userView = request.editor.userPersonalData?.let {
            userViewMapper.toShortView(it)
        }
        return ProtocolReviewRequestView(
            id = request.id,
            date = request.date,
            state = request.state,
            reviews = reviewMapper.toViewsAll(request.reviews),
            editor = userView,
            protocol = protocolViewMapper.toViewShort(request.protocol)
        )
    }
}
