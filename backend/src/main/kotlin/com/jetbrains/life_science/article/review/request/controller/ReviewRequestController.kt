package com.jetbrains.life_science.article.review.request.controller

import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTO
import com.jetbrains.life_science.article.review.request.dto.ReviewRequestDTOToInfoAdapter
import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestService
import com.jetbrains.life_science.article.review.request.view.ReviewRequestView
import com.jetbrains.life_science.article.review.request.view.ReviewRequestViewMapper
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import com.jetbrains.life_science.validator.validateEnumValue
import com.jetbrains.life_science.validator.validateUserAndVersionToEdit
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/review/request")
class ReviewRequestController(
    val service: ReviewRequestService,
    val viewMapper: ReviewRequestViewMapper,
    val userService: UserService,
    val articleVersionService: ArticleVersionService
) {

    @Operation(summary = "Returns a list of all review requests for a given version")
    @GetMapping("/version/{versionId}")
    fun getAllRequest(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewRequestView> {
        val version = articleVersionService.getById(versionId)
        val user = userService.getByEmail(principal.email)

        validateUserAndVersionToEdit(version, user) { "User can not watch review requests for this version" }

        val requests = service.getAllByVersion(version)
        return viewMapper.toViews(requests)
    }

    @Operation(summary = "Returns a list of all active review requests for a given version")
    @GetMapping("/version/active/{versionId}")
    fun getActiveRequest(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewRequestView> {
        val version = articleVersionService.getById(versionId)
        val user = userService.getByEmail(principal.email)

        validateUserAndVersionToEdit(version, user) { "User can not watch review requests for this version" }

        val requests = service.getAllActiveByVersionId(versionId)
        return viewMapper.toViews(requests)
    }

    @Operation(summary = "Creates a review request. Destination available values: [PROTOCOL, ARTICLE]")
    @PatchMapping("/version/{versionId}")
    fun createRequest(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ReviewRequestDTO,
        principal: Principal
    ): ReviewRequestView {
        val version = articleVersionService.getById(versionId)
        val user = userService.getByEmail(principal.email)

        validateEnumValue<VersionDestination>(dto.destination) { "Destination not exists" }
        validateUserAndVersionToEdit(version, user) { "User do not have permission to version" }

        val reviewRequest = service.add(ReviewRequestDTOToInfoAdapter(dto, version, user))
        return viewMapper.toView(reviewRequest)
    }

    @Operation(summary = "Removes a review request if it hasn't been answered yet")
    @DeleteMapping("/{requestId}")
    fun deleteRequest(
        @PathVariable requestId: Long,
        principal: Principal
    ) {
        val request = service.getById(requestId)
        val user = userService.getByEmail(principal.email)
        validateUserCanDeleteRequest(request, user)

        service.delete(request)
    }

    fun validateUserCanDeleteRequest(reviewRequest: ReviewRequest, user: User) {
        if (user.isAdminOrModerator()) return
        if (reviewRequest.version.author.id == user.id) return
        throw AccessDeniedException("User can not delete review")
    }
}
