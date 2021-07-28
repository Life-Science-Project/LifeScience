package com.jetbrains.life_science.review.request.service.publish

import com.jetbrains.life_science.exception.not_found.PublishApproachRequestNotFoundException
import com.jetbrains.life_science.exception.request.DuplicateRequestException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.factory.PublishApproachRequestFactory
import com.jetbrains.life_science.review.request.repository.PublishApproachRequestRepository
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.service.ReviewService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PublishApproachRequestServiceImpl(
    val reviewService: ReviewService,
    val repository: PublishApproachRequestRepository,
    val factory: PublishApproachRequestFactory
) : PublishApproachRequestService {

    override fun get(id: Long): PublishApproachRequest {
        return repository.findById(id).orElseThrow {
            PublishApproachRequestNotFoundException("PublishApproachRequest with id $id not found")
        }
    }

    override fun create(info: PublishApproachRequestInfo): PublishApproachRequest {
        if (repository.existsByApproachAndState(info.approach, RequestState.PENDING)) {
            throw DuplicateRequestException(
                "PublishApproachRequest for approach with " +
                    "id ${info.approach.id} is already exists"
            )
        }
        val publishApproachRequest = factory.create(info)
        return repository.save(publishApproachRequest)
    }

    override fun cancel(id: Long): PublishApproachRequest {
        return changeState(id, RequestState.CANCELED)
    }

    override fun addReview(id: Long, review: Review): PublishApproachRequest {
        val publishApproachRequest = get(id)
        publishApproachRequest.reviews.add(review)
        return repository.save(publishApproachRequest)
    }

    @Transactional
    override fun delete(id: Long) {
        val request = get(id)
        request.reviews.forEach {
            reviewService.deleteReview(it.id)
        }
        repository.deleteById(request.id)
    }

    private fun changeState(id: Long, state: RequestState): PublishApproachRequest {
        val publishApproachRequest = get(id)
        if (publishApproachRequest.state != RequestState.PENDING) {
            throw RequestImmutableStateException(
                "Can't change state of ${publishApproachRequest.state} " +
                    "PublishApproachRequest to $state"
            )
        }
        factory.changeState(publishApproachRequest, state)
        return repository.save(publishApproachRequest)
    }
}
