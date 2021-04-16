package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.dto.ReviewDTO
import com.jetbrains.life_science.article.review.dto.ReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.service.ReviewService
import com.jetbrains.life_science.article.review.view.ReviewView
import com.jetbrains.life_science.article.review.view.ReviewViewMapper
import com.jetbrains.life_science.exception.ReviewNotFoundException
import com.jetbrains.life_science.user.service.UserService
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ArticleReviewController(
    val service: ReviewService,
    val userService: UserService,
    val mapper: ReviewViewMapper,
) {

    @GetMapping
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ReviewView> {
        val user = userService.getByName(principal.name)
        return service.getAllByVersionId(versionId, user).map { mapper.createView(it) }
    }

    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ): ReviewView {
        val user = userService.getByName(principal.name)
        return mapper.createView(
            service.getById(reviewId, user)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createReview(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        if (versionId != dto.articleVersionId) {
            throw ReviewNotFoundException("Review's dto article version id and request article version id doesn't match")
        }
        return mapper.createView(
            service.addReview(
                ReviewDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{reviewId}")
    fun updateReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        @Validated @RequestBody dto: ReviewDTO,
        principal: Principal
    ): ReviewView {
        val user = userService.getByName(principal.name)
        val review = service.getById(reviewId, user)
        if (versionId != review.articleVersion.id) {
            throw ReviewNotFoundException("Review's dto article version id and request article version id doesn't match")
        }
        return mapper.createView(
            service.updateById(
                reviewId,
                ReviewDTOToInfoAdapter(dto),
                user
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ) {
        val user = userService.getByName(principal.name)
        val review = service.getById(reviewId, user)
        if (versionId != review.articleVersion.id) {
            throw ReviewNotFoundException("Review's article version id and request article version id doesn't match")
        }
        service.deleteReview(reviewId)
    }
}
