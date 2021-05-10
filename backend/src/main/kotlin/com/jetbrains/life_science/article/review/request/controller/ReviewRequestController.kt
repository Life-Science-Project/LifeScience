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
import com.jetbrains.life_science.validator.validateUserAndVersion
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

    @GetMapping("/version/{versionId}")
    fun getAllRequest(@PathVariable versionId: Long): List<ReviewRequestView> {
        val requests = service.getAllByVersionId(versionId)
        return viewMapper.toViews(requests)
    }

    @GetMapping("/version/active/{versionId}")
    fun getActiveRequest(@PathVariable versionId: Long): List<ReviewRequestView> {
        val requests = service.getAllActiveByVersionId(versionId)
        return viewMapper.toViews(requests)
    }

    @PatchMapping("/version/{versionId}")
    fun createRequest(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ReviewRequestDTO,
        principal: Principal
    ): ReviewRequestView {
        validateEnumValue<VersionDestination>(dto.destination) { "Destination not exists" }
        val version = articleVersionService.getById(versionId)
        val user = userService.getByEmail(principal.email)
        validateUserAndVersion(version, user) { "User do not have permission to version" }

        val reviewRequest = service.add(ReviewRequestDTOToInfoAdapter(dto, version, user))
        return viewMapper.toView(reviewRequest)
    }

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
