package com.jetbrains.life_science.article.review.response.controller

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.request.service.ReviewRequestService
import com.jetbrains.life_science.article.review.response.dto.ReviewDTO
import com.jetbrains.life_science.article.review.response.dto.ReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.service.ReviewService
import com.jetbrains.life_science.article.review.response.view.ReviewView
import com.jetbrains.life_science.article.review.response.view.ReviewViewMapper
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.ArticleVersionNotFoundException
import com.jetbrains.life_science.exception.request.BadRequestException
import com.jetbrains.life_science.exception.request.DuplicateReviewException
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import com.jetbrains.life_science.validator.validateEnumValue
import com.jetbrains.life_science.validator.validateUserAndVersionToEdit
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ReviewController(
    val reviewService: ReviewService,
    val articleVersionService: ArticleVersionService,
    val userService: UserService,
    val viewMapper: ReviewViewMapper,
) {

    @Autowired
    lateinit var reviewRequestService: ReviewRequestService

    @Operation(summary = "Gets a list of reviews for a given version and a given review request")
    @GetMapping("/requests/{requestId}")
    fun getReview(
        @PathVariable versionId: Long,
        principal: Principal,
        @PathVariable requestId: Long
    ): ReviewView? {
        val user = userService.getByEmail(principal.email)
        val version = articleVersionService.getById(versionId)
        val request = reviewRequestService.getById(requestId)

        validateUserAndVersionToEdit(version, user) { "User can not get review to this version" }
        validateRequestAndVersionIds(request, version)

        val review = reviewService.getByRequest(request)
        return review?.let { viewMapper.toView(review) }
    }

    @Operation(summary = "Gets a list of reviews for the given version")
    @GetMapping()
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewView> {
        val user = userService.getByEmail(principal.email)
        val version = articleVersionService.getById(versionId)

        validateUserAndVersionToEdit(version, user) { "User can not get review to this version" }

        val reviews = reviewService.getAllByVersion(version)
        return viewMapper.toViews(reviews)
    }

    @Operation(summary = "Creates a review and changes the status of the article depending on the resolution. Possible resolution values: [APPROVE, CHANGES_REQUESTED]")
    @Secured("ROLE_ADMIN", "ROLE_MODERATOR")
    @PostMapping("/request/{requestId}")
    fun addReview(
        @PathVariable versionId: Long,
        @PathVariable requestId: Long,
        @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        val version = articleVersionService.getById(versionId)
        val user = userService.getByEmail(principal.email)
        val request = reviewRequestService.getById(requestId)

        validateEnumValue<ReviewResolution>(dto.resolution) { "Incorrect resolution. Only [APPROVE, CHANGES_REQUESTED] available" }
        validateVersionId(version, versionId)
        validateRequestAndVersionIds(request, version)
        validateResponseNotExists(request)

        val review = reviewService.addReview(ReviewDTOToInfoAdapter(dto, request, user))
        return viewMapper.toView(review)
    }

    private fun validateResponseNotExists(request: ReviewRequest) {
        if (request.resolution != null) {
            throw DuplicateReviewException("Review to request already exists")
        }
    }

    private fun validateVersionId(version: ArticleVersion, versionId: Long) {
        if (version.id != versionId) {
            throw ArticleVersionNotFoundException("Article version with id: $version does not matches with request version id")
        }
    }

    private fun validateRequestAndVersionIds(request: ReviewRequest, version: ArticleVersion) {
        if (request.version.id != version.id) {
            throw BadRequestException("Request version id and version id do not matches")
        }
    }
}
