package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.request.entity.VersionDestination
import com.jetbrains.life_science.article.review.request.service.ReviewRequestService
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.factory.ReviewFactory
import com.jetbrains.life_science.article.review.response.repository.ReviewRepository
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.ReviewNotFoundException
import com.jetbrains.life_science.exception.request.BadRequestException
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
            VersionDestination.PROTOCOL -> articleVersionService.approveUserLocal(versionId)
            VersionDestination.STATIC -> articleVersionService.approveGlobal(versionId)
        }
    }

    override fun getById(reviewId: Long): Review {
        return repository.findByIdOrNull(reviewId) ?: throw ReviewNotFoundException("Review not found")
    }

    override fun addReview(info: ReviewInfo): Review {

    }

    override fun deleteReview(id: Long) {
        repository.deleteById(id)
    }

    @Transactional
    override fun requestChanges(info: ReviewInfo) {
        if (info.resolution != ReviewResolution.CHANGES_REQUESTED) {
            throw BadRequestException("Resolution must be CHANGES_REQUESTED")
        }
        addReview(info)
        val articleVersion = articleVersionService.getById(info.versionId)
        articleVersion.state = State.EDITING
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
}
