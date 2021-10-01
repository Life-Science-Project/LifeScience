package com.jetbrains.life_science.review.request.repository

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProtocolReviewRequestRepository : JpaRepository<ProtocolReviewRequest, Long> {
    fun existsByEditRecordAndState(editRecord: ProtocolEditRecord, state: RequestState): Boolean
}
