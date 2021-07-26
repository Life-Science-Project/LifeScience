package com.jetbrains.life_science.review.request.factory

import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestInfo
import org.springframework.stereotype.Component

@Component
class PublishProtocolRequestFactory {
    fun create(info: PublishProtocolRequestInfo): PublishProtocolRequest {
        return PublishProtocolRequest(
            id = 0,
            date = info.date,
            state = RequestState.PENDING,
            reviews = mutableListOf(),
            editor = info.editor,
            protocol = info.protocol
        )
    }

    fun changeState(publishProtocolRequest: PublishProtocolRequest, state: RequestState) {
        publishProtocolRequest.state = state
    }
}
