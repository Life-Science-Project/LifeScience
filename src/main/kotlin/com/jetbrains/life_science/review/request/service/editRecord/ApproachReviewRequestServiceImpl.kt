package com.jetbrains.life_science.review.request.service.editRecord

import com.jetbrains.life_science.exception.not_found.ApproachReviewRequestNotFoundException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.factory.ApproachReviewRequestFactory
import com.jetbrains.life_science.review.request.repository.ApproachReviewRequestRepository
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.service.ReviewService
import org.springframework.stereotype.Service

@Service
class ApproachReviewRequestServiceImpl(
    val reviewService: ReviewService,
    val repository: ApproachReviewRequestRepository,
    val factory: ApproachReviewRequestFactory,
) : ApproachReviewRequestService {
    override fun get(id: Long): ApproachReviewRequest {
        return repository.findById(id).orElseThrow {
            ApproachReviewRequestNotFoundException("ApproachReviewRequest with id $id not found")
        }
    }

    override fun create(info: ApproachReviewRequestInfo): ApproachReviewRequest {
        val request = factory.create(info)
        return repository.save(request)
    }

    override fun cancel(id: Long): ApproachReviewRequest {
        return changeState(id, RequestState.CANCELED)
    }

    override fun addReview(id: Long, review: Review): ApproachReviewRequest {
        val request = get(id)
        request.reviews.add(review)
        return repository.save(request)
    }

    override fun delete(id: Long) {
        val request = get(id)
        request.reviews.forEach {
            reviewService.deleteReview(it.id)
        }
        repository.deleteById(request.id)
    }

    private fun changeState(id: Long, state: RequestState): ApproachReviewRequest {
        val approachRequest = get(id)
        if (approachRequest.state != RequestState.PENDING) {
            throw RequestImmutableStateException(
                "Can't change state of ${approachRequest.state} " +
                    "ApproachReviewRequest to $state"
            )
        }
        factory.changeState(approachRequest, state)
        return repository.save(approachRequest)
    }
}