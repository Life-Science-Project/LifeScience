package com.jetbrains.life_science.review.request.repository

import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProtocolReviewRequestRepository : JpaRepository<ProtocolReviewRequest, Long>
