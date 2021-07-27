package com.jetbrains.life_science.review.request.repository

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApproachReviewRequestRepository : JpaRepository<ApproachReviewRequest, Long> {
    fun getAllByEditRecord(editRecord: ApproachEditRecord): List<ApproachReviewRequest>
}
