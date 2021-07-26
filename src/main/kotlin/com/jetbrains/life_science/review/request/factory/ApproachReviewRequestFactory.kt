package com.jetbrains.life_science.review.request.factory

import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.editRecord.ApproachReviewRequestInfo
import org.springframework.stereotype.Component

@Component
class ApproachReviewRequestFactory {
    fun create(info: ApproachReviewRequestInfo): ApproachReviewRequest {
        return ApproachReviewRequest(
            id = 0,
            date = info.date,
            state = RequestState.PENDING,
            reviews = mutableListOf(),
            editor = info.editor,
            editRecord = info.approachEditRecord
        )
    }

    fun changeState(publishApproachRequest: ApproachReviewRequest, state: RequestState) {
        publishApproachRequest.state = state
    }
}
