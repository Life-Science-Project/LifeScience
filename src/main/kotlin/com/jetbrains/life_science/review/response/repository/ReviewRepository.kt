package com.jetbrains.life_science.review.response.repository

import com.jetbrains.life_science.review.response.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long>
