package com.jetbrains.life_science.article.review.controller

import com.jetbrains.life_science.article.review.dto.ArticleReviewDTO
import com.jetbrains.life_science.article.review.dto.ArticleReviewDTOToInfoAdapter
import com.jetbrains.life_science.article.review.service.ArticleReviewService
import com.jetbrains.life_science.article.review.view.ArticleReviewView
import com.jetbrains.life_science.article.review.view.ArticleReviewViewMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/articles/versions/{versionId}/reviews")
class ArticleReviewController(
    val service: ArticleReviewService,
    val mapper: ArticleReviewViewMapper,
) {

    @GetMapping
    fun getReviews(
        @PathVariable versionId: Long,
        principal: Principal // only if the version belongs to this user, or the user is admin/moderator
    ): List<ArticleReviewView> {
        TODO("only if the version belongs to this user, or the user is admin/moderator")
        return service.getAllByVersionId(versionId).map { mapper.createView(it) }
    }

    @GetMapping("/{reviewId}")
    fun getReview(
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
        principal: Principal // only if the version belongs to this user, or the user is admin/moderator
    ): ArticleReviewView {
        TODO("only if the version belongs to this user, or the user is admin/moderator")
        return mapper.createView(
            service.getById(reviewId)
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PostMapping
    fun createReview(
        @PathVariable versionId: Long,
        @Validated @RequestBody dto: ArticleReviewDTO,
        principal: Principal
    ): ArticleReviewView {
        return mapper.createView(
            service.addReview(
                ArticleReviewDTOToInfoAdapter(dto)
            )
        )
    }

    @Secured("ROLE_MODERATOR", "ROLE_ADMIN")
    @PutMapping
    fun updateReview(
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
        @PathVariable versionId: Long,
        @PathVariable reviewId: Long,
    ): ResponseEntity<Void> {
        service.deleteReview(reviewId)
        return ResponseEntity.ok().build()
    }
}
