package com.jetbrains.life_science.review.repository

import com.jetbrains.life_science.review.entity.MethodReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MethodReviewRepository : JpaRepository<MethodReview, Long>
