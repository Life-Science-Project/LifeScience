package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestService
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.factory.ReviewFactory
import com.jetbrains.life_science.article.review.response.repository.ReviewRepository
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

    @Transactional
    override fun approve(versionId: Long, reviewer: User, destination: VersionDestination) {
        val request = reviewRequestService.getByVersionId(versionId)
        if (request != null) {
            val review = factory.create(ReviewResolution.SUCCESS, "success", request, reviewer)
            repository.save(review)
        }
        when (destination) {
            VersionDestination.USER_LOCAL -> articleVersionService.approveUserLocal(versionId)
            VersionDestination.GLOBAL -> articleVersionService.approveGlobal(versionId)
        }
    }

    override fun getById(reviewId: Long): Review {
        return repository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review not found")
    }

    override fun addReview(info: ReviewInfo): Review {
        val request = reviewRequestService.getByVersionIdOrThrow(info.versionId)
        val review = factory.create(info.resolution, info.comment, request, info.reviewer)
        return repository.save(review)
    }

    @Transactional
    override fun update(info: ReviewInfo): Review {
        val review = getById(info.reviewId)
        return factory.setParams(review, info, info.reviewer)
    }

    override fun deleteReview(id: Long) {
        repository.deleteById(id)
    }

    @Transactional
    override fun requestChanges(info: ReviewInfo) {
        val request = reviewRequestService.getByVersionIdOrThrow(info.versionId)
        val review = factory.create(info.resolution, info.comment, request, info.reviewer)
        repository.save(review)
        val articleVersion = articleVersionService.getById(info.versionId)
        articleVersion.state = State.EDITING
    }

    override fun getAllByVersionId(versionId: Long, user: User): List<Review> {
        return if (user.isAdminOrModerator()) {
            repository.findAllByReviewRequestVersionId(versionId)
        } else {
            repository.findAllByReviewRequestVersionIdAndReviewRequestVersionAuthor(versionId, user)
        }
    }

    override fun getByVersionId(versionId: Long): Review? {
        return repository.findByReviewRequestVersionId(versionId)
    }
}
