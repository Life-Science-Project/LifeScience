package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.dto.ArticleReviewDTO
import com.jetbrains.life_science.article.review.dto.ArticleReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.service.ArticleReviewService
import com.jetbrains.life_science.article.review.view.ArticleReviewView
import com.jetbrains.life_science.article.review.view.ArticleReviewViewMapper
import com.jetbrains.life_science.exception.ArticleReviewNotFoundException
import com.jetbrains.life_science.user.service.UserService
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ArticleReviewController(
    val service: ArticleReviewService,
    val userService: UserService,
    val mapper: ArticleReviewViewMapper,
) {

    @GetMapping
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal
    ): List<ArticleReviewView> {
        val user = userService.getByName(principal.name)
        return service.getAllByVersionId(versionId, user).map { mapper.createView(it) }
    }

    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal
    ): ArticleReviewView {
        val user = userService.getByName(principal.name)
        return mapper.createView(
            service.getById(reviewId, user)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createReview(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ArticleReviewDTO,
        principal: Principal
    ): ArticleReviewView {
        if (versionId != dto.articleVersionId) {
            throw ArticleReviewNotFoundException("Review's dto article version id and request article version id doesn't match")
        }
        return mapper.createView(
            service.addReview(
                ArticleReviewDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping("/{reviewId}")
    fun updateReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        @Validated @RequestBody dto: ArticleReviewDTO,
        principal: Principal
    ): ArticleReviewView {
        val user = userService.getByName(principal.name)
        val review = service.getById(reviewId, user)
        if (versionId != review.articleVersion.id) {
            throw ArticleReviewNotFoundException("Review's dto article version id and request article version id doesn't match")
        }
        return mapper.createView(
            service.updateById(
                reviewId,
                ArticleReviewDTOToInfoAdapter(dto),
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
            throw ArticleReviewNotFoundException("Review's article version id and request article version id doesn't match")
        }
        service.deleteReview(reviewId)
    }
}
