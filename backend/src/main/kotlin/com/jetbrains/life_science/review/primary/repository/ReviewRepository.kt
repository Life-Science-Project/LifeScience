package com.jetbrains.life_science.review.primary.repository

import com.jetbrains.life_science.review.primary.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long>
