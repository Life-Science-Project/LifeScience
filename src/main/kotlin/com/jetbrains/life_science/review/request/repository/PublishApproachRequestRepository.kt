package com.jetbrains.life_science.review.request.repository

import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishApproachRequestRepository : JpaRepository<PublishApproachRequest, Long>