package com.jetbrains.life_science.review.request.service.publish

import com.jetbrains.life_science.exception.not_found.PublishProtocolRequestNotFoundException
import com.jetbrains.life_science.exception.request.DuplicateRequestException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.factory.PublishProtocolRequestFactory
import com.jetbrains.life_science.review.request.repository.PublishProtocolRequestRepository
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.service.ReviewService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PublishProtocolRequestServiceImpl(
    val reviewService: ReviewService,
    val repository: PublishProtocolRequestRepository,
    val factory: PublishProtocolRequestFactory
) : PublishProtocolRequestService {

    override fun get(id: Long): PublishProtocolRequest {
        return repository.findById(id).orElseThrow {
            PublishProtocolRequestNotFoundException("PublishApproachRequest with id $id not found")
        }
    }

    override fun create(info: PublishProtocolRequestInfo): PublishProtocolRequest {
        if (repository.existsByProtocolAndState(info.protocol, RequestState.PENDING)) {
            throw DuplicateRequestException(
                "PublishProtocolRequest for protocol with " +
                    "id ${info.protocol.id} is already exists"
            )
        }
        val publishProtocolRequest = factory.create(info)
        return repository.save(publishProtocolRequest)
    }

    override fun cancel(id: Long): PublishProtocolRequest {
        return changeState(id, RequestState.CANCELED)
    }

    override fun addReview(id: Long, review: Review): PublishProtocolRequest {
        val publishProtocolRequest = get(id)
        publishProtocolRequest.reviews.add(review)
        return repository.save(publishProtocolRequest)
    }

    @Transactional
    override fun delete(id: Long) {
        val request = get(id)
        request.reviews.forEach {
            reviewService.deleteReview(it.id)
        }
        repository.deleteById(request.id)
    }

    private fun changeState(id: Long, state: RequestState): PublishProtocolRequest {
        val publishProtocolRequest = get(id)
        if (publishProtocolRequest.state != RequestState.PENDING) {
            throw RequestImmutableStateException(
                "Can't change state of ${publishProtocolRequest.state} " +
                    "PublishProtocolRequest to $state"
            )
        }
        factory.changeState(publishProtocolRequest, state)
        return repository.save(publishProtocolRequest)
    }
}
