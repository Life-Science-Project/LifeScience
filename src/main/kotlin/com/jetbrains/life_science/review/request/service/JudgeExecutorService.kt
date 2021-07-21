package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.review.request.entity.ReviewRequest

interface JudgeExecutorService {
    fun cancel(id: Long): ReviewRequest

    fun delete(id: Long)
}
