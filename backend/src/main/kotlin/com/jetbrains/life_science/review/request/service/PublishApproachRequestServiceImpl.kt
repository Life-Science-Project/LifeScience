package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.exception.not_found.PublishApproachRequestNotFoundException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.primary.entity.Review
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.factory.PublishApproachRequestFactory
import com.jetbrains.life_science.review.request.repository.PublishApproachRequestRepository
import org.springframework.stereotype.Service

@Service
class PublishApproachRequestServiceImpl(
    val repository: PublishApproachRequestRepository,
    val factory: PublishApproachRequestFactory
) : PublishApproachRequestService {

    override fun get(id: Long): PublishApproachRequest {
        return repository.findById(id).orElseThrow {
            PublishApproachRequestNotFoundException("PublishApproachRequest with id $id not found")
        }
    }

    override fun create(info: PublishApproachRequestInfo): PublishApproachRequest {
        val publishApproachRequest = factory.create(info)
        return repository.save(publishApproachRequest)
    }

    override fun approve(id: Long): PublishApproachRequest {
        return changeState(id, RequestState.APPROVED)
    }

    override fun cancel(id: Long): PublishApproachRequest {
        return changeState(id, RequestState.CANCELED)
    }

    override fun addReview(id: Long, review: Review): PublishApproachRequest {
        val publishApproachRequest = get(id)
        if (!publishApproachRequest.reviews.contains(review)) {
            publishApproachRequest.reviews.add(review)
            repository.save(publishApproachRequest)
        }
        return publishApproachRequest
    }

    override fun findAll(): List<PublishApproachRequest> {
        return repository.findAll()
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
