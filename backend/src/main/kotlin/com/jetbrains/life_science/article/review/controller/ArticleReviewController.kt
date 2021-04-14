package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.dto.ArticleReviewDTO
import com.jetbrains.life_science.article.review.dto.ArticleReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.service.ArticleReviewService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reviews")
class ArticleReviewController(
    val articleReviewService: ArticleReviewService
) {

    @PostMapping
    fun addReview(@Validated @RequestBody dto: ArticleReviewDTO) {
        articleReviewService.addReview(ArticleReviewDTOToInfoAdapter(dto))
    }
}
