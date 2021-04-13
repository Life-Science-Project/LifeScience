package com.jetbrains.life_science.review.service

import com.jetbrains.life_science.review.entity.MethodReview

interface MethodReviewService {

    fun addReview(info: MethodReviewInfo): MethodReview

}
