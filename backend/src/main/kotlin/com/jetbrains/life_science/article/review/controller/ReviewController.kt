package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.dto.ReviewDTO
import com.jetbrains.life_science.article.review.dto.ReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.service.ReviewService
import com.jetbrains.life_science.article.review.view.ReviewView
import com.jetbrains.life_science.article.review.view.ReviewViewMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ReviewController(
    val reviewService: ReviewService,
    val reviewMapper: ReviewViewMapper,
) {

    @GetMapping
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewView> {
        // TODO: only if the version belongs to this user, or the user is admin/moderator
        return reviewService.getAllByVersionId(versionId).map { reviewMapper.createView(it) }
    }

    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ): ReviewView {
        // TODO: only if the version belongs to this user, or the user is admin/moderator
        return reviewMapper.createView(
            reviewService.getById(reviewId)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createReview(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        return reviewMapper.createView(
            reviewService.addReview(
                ReviewDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateReview(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        // TODO(#54): implement method
        throw UnsupportedOperationException("Not yet implemented")
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
    ): ResponseEntity<Void> {
        reviewService.deleteReview(reviewId)
        return ResponseEntity.ok().build()
    }
}
