package com.jetbrains.life_science.review.request.repository

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishApproachRequestRepository : JpaRepository<PublishApproachRequest, Long> {
    fun existsByApproachAndState(approach: DraftApproach, state: RequestState): Boolean
}
