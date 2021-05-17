package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.request.service.ReviewRequestService
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.factory.ReviewFactory
import com.jetbrains.life_science.article.review.response.repository.ReviewRepository
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.ReviewNotFoundException
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewServiceImpl(
    val repository: ReviewRepository,
    val factory: ReviewFactory,
    val reviewRequestService: ReviewRequestService,
    val articleVersionService: ArticleVersionService
) : ReviewService {

    override fun getById(reviewId: Long): Review {
        return repository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review not found")
    }

    @Transactional
    override fun addReview(info: ReviewInfo): Review {
        val review = factory.create(info)
        repository.save(review)
        when (info.resolution) {
            ReviewResolution.CHANGES_REQUESTED -> requestChanges(info.request.version)
            ReviewResolution.APPROVE -> approve(review)
        }
        return review
    }

    private fun approve(review: Review) {
        val request = review.reviewRequest
        articleVersionService.approve(review.version, request.destination)
    }

    private fun requestChanges(version: ArticleVersion) {
        articleVersionService.changeState(version, State.EDITING)
    }

    override fun deleteReview(id: Long) {
        repository.deleteById(id)
    }

    override fun getAllByVersion(version: ArticleVersion): List<Review> {
        return repository.findAllByReviewRequestVersion(version)
    }

    override fun getAllByVersionAndUser(version: ArticleVersion, user: User): List<Review> {
        return repository.findAllByReviewRequestVersionAndReviewRequestVersionAuthor(version, user)
    }

    override fun getByVersionId(versionId: Long): Review? {
        return repository.findByReviewRequestVersionId(versionId)
    }

    override fun getByRequest(request: ReviewRequest): Review {
        return repository.findByReviewRequest(request) ?: throw ReviewNotFoundException("Review not found")
    }
}
