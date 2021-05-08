package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.response.dto.ReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTOToInfoAdapter
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestService
import com.jetbrains.life_science.article.review.response.dto.ReviewDTO
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.review.response.service.ReviewService
import com.jetbrains.life_science.article.review.response.view.ReviewView
import com.jetbrains.life_science.article.review.response.view.ReviewViewMapper
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.ReviewNotFoundException
import com.jetbrains.life_science.exception.request.BadRequestException
import com.jetbrains.life_science.user.master.entity.UserCredentials
import com.jetbrains.life_science.user.master.service.UserCredentialsService
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ArticleReviewController(
    val reviewService: ReviewService,
    val reviewRequestService: ReviewRequestService,
    val articleVersionService: ArticleVersionService,
    val userCredentialsService: UserCredentialsService,
    val userService: UserService,
    val viewMapper: ReviewViewMapper,
) {

    @GetMapping
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewView> {
        val user = userService.getByEmail(principal.email)
        val reviews = reviewService.getAllByVersionId(versionId, user)
        return viewMapper.createViews(reviews)
    }

    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ): ReviewView {
        val user = userCredentialsService.getByEmail(principal.email)
        val review = reviewService.getById(reviewId)
        checkReviewAccess(user, review)
        return viewMapper.createView(review)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createReview(
        @PathVariable versionId: Long,
        @Validated @RequestBody reviewDto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        val reviewer = userService.getByEmail(principal.email)
        val review = reviewService.addReview(
            ReviewDTOToInfoAdapter(reviewDto, versionId, reviewer)
        )
        return viewMapper.createView(review)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{reviewId}")
    fun updateReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        @Validated @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        val reviewer = userService.getByEmail(principal.email)
        val updatedReview = reviewService.update(
            ReviewDTOToInfoAdapter(dto, versionId, reviewer, reviewId),
        )
        return viewMapper.createView(updatedReview)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ) {
        val review = reviewService.getById(reviewId)
        checkIdEquality(versionId, review.version.id)
        reviewService.deleteReview(reviewId)
    }

    @PatchMapping("/request/local")
    fun requestReviewLocal(
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        val user = userCredentialsService.getByEmail(principal.email)
        val version = articleVersionService.getById(versionId)
        checkBeforeRequest(version, user)
        reviewRequestService.add(
            ReviewRequestDTOToInfoAdapter(versionId, VersionDestination.USER_LOCAL)
        )
    }

    @PatchMapping("/request/global")
    fun requestReviewGlobal(
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        val user = userCredentialsService.getByEmail(principal.email)
        val version = articleVersionService.getById(versionId)
        checkBeforeRequest(version, user)
        reviewRequestService.add(
            ReviewRequestDTOToInfoAdapter(versionId, VersionDestination.GLOBAL)
        )
    }

    private fun checkBeforeRequest(version: ArticleVersion, user: UserCredentials) {
        if (!version.canModify(user)) {
            throw AccessDeniedException("User has no access to this version")
        }
        if (version.state != State.EDITING) {
            throw BadRequestException("Version is not editing")
        }
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/approve")
    fun approve(
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        val user = userService.getByEmail(principal.email)
        reviewService.approve(versionId, user, VersionDestination.GLOBAL)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/approve-local")
    fun approveLocal(
        @PathVariable versionId: Long,
        principal: Principal
    ) {
        val user = userService.getByEmail(principal.email)
        reviewService.approve(versionId, user, VersionDestination.USER_LOCAL)
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PatchMapping("/request-changes")
    fun requestChanges(
        @PathVariable versionId: Long,
        @Validated @RequestBody reviewDto: ReviewDTO,
        principal: Principal
    ) {
        val reviewer = userService.getByEmail(principal.email)
        reviewService.requestChanges(ReviewDTOToInfoAdapter(reviewDto, versionId, reviewer))
    }

    private fun checkReviewAccess(user: UserCredentials, review: Review) {
        if (!user.isAdminOrModerator() && user.id != review.version.author.id) {
            throw AccessDeniedException("User has no access to this review")
        }
    }

    private fun checkIdEquality(versionId: Long, entityId: Long) {
        if (versionId != entityId) {
            throw ReviewNotFoundException("Review's article version id and request article version id doesn't match")
        }
    }
}
