package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.dto.ArticleReviewDTO
import com.jetbrains.life_science.article.review.dto.ArticleReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.service.ArticleReviewService
import com.jetbrains.life_science.article.review.view.ArticleReviewView
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/articles/{articleId}/versions/{versionId}/reviews")
class ArticleReviewController(
    val service: ArticleReviewService
) {

    @GetMapping
    fun getReviews(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        principal: Principal // only if the version belongs to this user, or the user is admin/moderator
    ): List<ArticleReviewView> {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal // only if the version belongs to this user, or the user is admin/moderator
    ): ArticleReviewView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createReview(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ArticleReviewDTO,
        principal: Principal
    ): ArticleReviewView {
        service.addReview(ArticleReviewDTOToInfoAdapter(dto))
        // TODO(#54): add return value
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateReview(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ArticleReviewDTO,
        principal: Principal
    ): ArticleReviewView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable articleId: Long,
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
    ): ResponseEntity<Void> {
        // TODO(#54): implement method
        // return ResponseEntity.ok().build()
        throw UnsupportedOperationException("Not yet implemented")
    }
}
