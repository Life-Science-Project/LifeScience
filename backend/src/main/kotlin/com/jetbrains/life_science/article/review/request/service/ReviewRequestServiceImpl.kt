package com.jetbrains.life_science.article.review.request.service

import com.jetbrains.life_science.article.review.request.entity.ReviewRequest
import com.jetbrains.life_science.article.review.request.factory.ReviewRequestFactory
import com.jetbrains.life_science.article.review.request.repository.ReviewRequestRepository
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import com.jetbrains.life_science.exception.not_found.ReviewRequestNotFoundException
import com.jetbrains.life_science.exception.request.DuplicateReviewRequestException
import com.jetbrains.life_science.exception.request.ReviewResponseAlreadyExistsException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ReviewRequestServiceImpl(
    val repository: ReviewRequestRepository,
    val factory: ReviewRequestFactory,
    val articleVersionService: ArticleVersionService
) : ReviewRequestService {

    @Transactional
    override fun add(info: ReviewRequestInfo): ReviewRequest {
        if (repository.existsByVersionIdAndResolutionIsNull(info.versionId)) {
            throw DuplicateReviewRequestException("Review request for version ${info.versionId} already exists")
        }
        val version = articleVersionService.getById(info.versionId)
        version.state = State.PENDING_FOR_REVIEW
        val request = factory.create(version, info.destination)
        repository.save(request)
        return request
    }

    override fun getByVersionIdOrThrow(versionId: Long): ReviewRequest {
        return getByVersionId(versionId) ?: throw ReviewRequestNotFoundException("Review request not found")
    }

    @Transactional
    override fun removeRequest(reviewId: Long) {
        val request = getByIdOrThrow(reviewId)
        if (request.resolution != null) {
            repository.delete(request)
        } else {
            throw ReviewResponseAlreadyExistsException("Review already exists to request with id $reviewId")
        }
    }

    override fun getByVersionId(versionId: Long): ReviewRequest? {
        return repository.findByVersionIdAndResolutionIsNull(versionId)
    }

    override fun getAllByVersionId(versionId: Long): List<ReviewRequest> {
        return repository.findAllByVersionId(versionId)
    }

    override fun getAllByAuthorId(authorId: Long): List<ReviewRequest> {
        return repository.findAllByVersionAuthorId(authorId)
    }

    override fun getByIdOrThrow(reviewRequestId: Long): ReviewRequest {
        return repository.findByIdOrNull(reviewRequestId)
            ?: throw ReviewRequestNotFoundException("Review with id $reviewRequestId not found")
    }
}
