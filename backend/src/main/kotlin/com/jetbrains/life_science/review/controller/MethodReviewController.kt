package com.jetbrains.life_science.review.controller

import com.jetbrains.life_science.review.dto.MethodReviewDTO
import com.jetbrains.life_science.review.dto.MethodReviewDTOToInfoAdapter
import com.jetbrains.life_science.review.service.MethodReviewService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reviews")
class MethodReviewController(
    val methodReviewService: MethodReviewService
) {

    @PostMapping
    fun addReview(@Validated @RequestBody dto: MethodReviewDTO) {
        methodReviewService.addReview(MethodReviewDTOToInfoAdapter(dto))
    }
}
