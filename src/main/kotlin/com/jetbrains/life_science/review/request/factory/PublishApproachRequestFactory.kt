package com.jetbrains.life_science.review.request.factory

import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestInfo
import org.springframework.stereotype.Component

@Component
class PublishApproachRequestFactory {

    fun create(info: PublishApproachRequestInfo): PublishApproachRequest {
        return PublishApproachRequest(
            id = 0,
            date = info.date,
            state = RequestState.PENDING,
            reviews = mutableListOf(),
            editor = info.editor,
            approach = info.approach
        )
    }

    fun changeState(publishApproachRequest: PublishApproachRequest, state: RequestState) {
        publishApproachRequest.state = state
    }
}
