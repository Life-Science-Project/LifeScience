package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.dto.ReviewDTO
import com.jetbrains.life_science.article.review.dto.ReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.service.ReviewService
import com.jetbrains.life_science.article.review.view.ReviewView
import com.jetbrains.life_science.article.review.view.ReviewViewMapper
import com.jetbrains.life_science.exception.not_found.ReviewNotFoundException
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.util.email
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ArticleReviewController(
    val service: ReviewService,
    val userCredentialsService: UserCredentialsService,
    val mapper: ReviewViewMapper,
) {

    @GetMapping
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewView> {
        val user = userCredentialsService.getByEmail(principal.email)
        return service.getAllByVersionId(versionId, user).map { mapper.createView(it) }
    }

    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ): ReviewView {
        val user = userCredentialsService.getByEmail(principal.email)
        val review = service.getById(reviewId, user)
        return mapper.createView(review)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createReview(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        checkIdEquality(versionId, dto.articleVersionId)
        val review = service.addReview(
            ReviewDTOToInfoAdapter(dto)
        )
        return mapper.createView(review)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{reviewId}")
    fun updateReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        @Validated @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        val user = userCredentialsService.getByEmail(principal.email)
        val review = service.getById(reviewId, user)
        checkIdEquality(versionId, review.articleVersion.id)
        val updatedReview = service.updateById(
            ReviewDTOToInfoAdapter(dto, reviewId),
            user
        )
        return mapper.createView(updatedReview)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ) {
        val user = userCredentialsService.getByEmail(principal.email)
        val review = service.getById(reviewId, user)
        checkIdEquality(versionId, review.articleVersion.id)
        service.deleteReview(reviewId)
    }

    private fun checkIdEquality(versionId: Long, entityId: Long) {
        if (versionId != entityId) {
            throw ReviewNotFoundException("Review's article version id and request article version id doesn't match")
        }
    }
}
