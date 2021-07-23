package com.jetbrains.life_science.review.request.factory

import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.editRecord.ProtocolReviewRequestInfo
import org.springframework.stereotype.Component

@Component
class ProtocolReviewRequestFactory {
    fun create(info: ProtocolReviewRequestInfo): ProtocolReviewRequest {
        return ProtocolReviewRequest(
            id = info.id,
            date = info.date,
            state = RequestState.PENDING,
            reviews = mutableListOf(),
            editor = info.editor,
            editRecord = info.protocolEditRecord
        )
    }

    fun changeState(publishApproachRequest: ProtocolReviewRequest, state: RequestState) {
        publishApproachRequest.state = state
    }
}
