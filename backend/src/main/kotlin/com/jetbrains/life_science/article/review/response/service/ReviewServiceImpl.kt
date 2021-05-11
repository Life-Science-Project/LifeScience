package com.jetbrains.life_science.article.review.response.service

import com.jetbrains.life_science.article.review.request.service.ReviewRequestService
import com.jetbrains.life_science.article.review.response.entity.Review
import com.jetbrains.life_science.article.review.response.entity.ReviewResolution
import com.jetbrains.life_science.article.review.response.factory.ReviewFactory
import com.jetbrains.life_science.article.review.response.repository.ReviewRepository
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.ReviewNotFoundException
import com.jetbrains.life_science.user.master.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

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

    override fun addReview(info: ReviewInfo): Review {
        return when (info.resolution) {
            ReviewResolution.CHANGES_REQUESTED -> requestChanges(info)
            ReviewResolution.SUCCESS -> approve(info)
        }
    }

    private fun approve(info: ReviewInfo): Review {
        TODO()
    }

    private fun requestChanges(info: ReviewInfo): Review {
        TODO()
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
}
