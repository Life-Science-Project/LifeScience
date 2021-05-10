package com.jetbrains.life_science.article.review.response.controller

import com.jetbrains.life_science.article.review.response.service.ReviewService
import com.jetbrains.life_science.article.review.response.view.ReviewView
import com.jetbrains.life_science.article.review.response.view.ReviewViewMapper
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.user.master.service.UserService
import com.jetbrains.life_science.util.email
import com.jetbrains.life_science.validator.validateUserAndVersion
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ArticleReviewController(
    val reviewService: ReviewService,
    val articleVersionService: ArticleVersionService,
    val userService: UserService,
    val viewMapper: ReviewViewMapper,
) {

    @GetMapping()
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewView> {
        val user = userService.getByEmail(principal.email)
        val version = articleVersionService.getById(versionId)
        validateUserAndVersion(version, user) { "User can not get review to this version" }

        val reviews = if (user.isAdminOrModerator()) {
            reviewService.getAllByVersion(version)
        } else {
            reviewService.getAllByVersionAndUser(version, user)
        }
        return viewMapper.toViews(reviews)
    }

    @Secured("ROLE_ADMIN", "ROLE_MODERATOR")
    @PostMapping("/request/{requestId}")
    fun addReview(
        @PathVariable versionId: Long,
        @PathVariable requestId: Long
    ) {


    }

}
