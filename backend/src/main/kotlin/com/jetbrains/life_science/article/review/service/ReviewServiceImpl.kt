package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.Review
import com.jetbrains.life_science.article.review.factory.ReviewFactory
import com.jetbrains.life_science.article.review.repository.ReviewRepository
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.ReviewNotFoundException
import com.jetbrains.life_science.user.details.service.UserService
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(
    val repository: ReviewRepository,
    val factory: ReviewFactory,
    val articleVersionService: ArticleVersionService,
    val userCredentialsService: UserCredentialsService,
    val userService: UserService
) : ReviewService {

    override fun addReview(info: ReviewInfo): Review {
        val articleVersion = articleVersionService.getById(info.articleVersionId)
        val user = userCredentialsService.getById(info.reviewerId).user
        return repository.save(factory.create(info, articleVersion, user))
    }

    override fun deleteReview(reviewId: Long) {
        checkReviewExists(reviewId)
        repository.deleteById(reviewId)
    }

    override fun getAllByVersionId(articleVersionId: Long): List<Review> {
        return repository.findAllByArticleVersionId(articleVersionId)
    }

    override fun getById(reviewId: Long): Review {
        checkReviewExists(reviewId)
        return repository.getOne(reviewId)
    }

    private fun checkReviewExists(id: Long) {
        if (!repository.existsById(id)) {
            throw ReviewNotFoundException("Review not found by id: $id")
        }
    }
}
