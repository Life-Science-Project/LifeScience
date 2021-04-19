package com.jetbrains.life_science.article.review.service

import com.jetbrains.life_science.article.review.entity.Review
import com.jetbrains.life_science.article.review.factory.ReviewFactory
import com.jetbrains.life_science.article.review.repository.ReviewRepository
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.ReviewNotFoundException
import com.jetbrains.life_science.user.credentials.entity.UserCredentials
import com.jetbrains.life_science.user.credentials.service.UserCredentialsService
import com.jetbrains.life_science.user.details.entity.User
import com.jetbrains.life_science.user.details.service.UserService
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    override fun getAllByVersionId(articleVersionId: Long, user: UserCredentials): List<Review> {
        return repository.findAllByArticleVersionId(articleVersionId).filter {
            checkAccess(it.reviewer, it.articleVersion.author, user)
        }
    }

    override fun getById(reviewId: Long, user: UserCredentials): Review {
        checkReviewExists(reviewId)
        val review = repository.findById(reviewId).get()
        if (checkAccess(review.reviewer, review.articleVersion.author, user)) {
            return review
        } else {
            throw AccessDeniedException("You haven't got enough permissions to see this review")
        }
    }

    @Transactional
    override fun updateById(reviewInfo: ReviewInfo, user: UserCredentials): Review {
        val review = getById(reviewInfo.id, user)
        val version = articleVersionService.getById(reviewInfo.articleVersionId)
        val reviewer = userService.getById(reviewInfo.reviewerId)
        factory.setParams(review, reviewInfo, version, reviewer)
        return review
    }

    private fun checkReviewExists(id: Long) {
        if (!repository.existsById(id)) {
            throw ReviewNotFoundException("Review not found by id: $id")
        }
    }

    private fun checkAccess(reviewer: User, author: User, userCredentials: UserCredentials): Boolean {
        return reviewer.id == userCredentials.user.id ||
                author.id == userCredentials.user.id ||
                userCredentials.roles.any {
                    it.name == "ROLE_ADMIN" || it.name == "ROLE_MODERATOR"
                }
    }
}
