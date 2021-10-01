package com.jetbrains.life_science.review.request.repository

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApproachReviewRequestRepository : JpaRepository<ApproachReviewRequest, Long> {
    fun existsByEditRecordAndState(editRecord: ApproachEditRecord, state: RequestState): Boolean
}
