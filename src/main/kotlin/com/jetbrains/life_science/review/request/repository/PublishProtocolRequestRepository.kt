package com.jetbrains.life_science.review.request.repository

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishProtocolRequestRepository : JpaRepository<PublishProtocolRequest, Long> {
    fun existsByProtocolAndState(protocol: DraftProtocol, state: RequestState): Boolean
}
