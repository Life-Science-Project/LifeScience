package com.jetbrains.life_science.review.request.service.editRecord

import com.jetbrains.life_science.exception.not_found.ProtocolReviewRequestNotFoundException
import com.jetbrains.life_science.exception.request.DuplicateRequestException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.factory.ProtocolReviewRequestFactory
import com.jetbrains.life_science.review.request.repository.ProtocolReviewRequestRepository
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.service.ReviewService
import org.springframework.stereotype.Service

@Service
class ProtocolReviewRequestServiceImpl(
    val reviewService: ReviewService,
    val repository: ProtocolReviewRequestRepository,
    val factory: ProtocolReviewRequestFactory,
) : ProtocolReviewRequestService {
    override fun get(id: Long): ProtocolReviewRequest {
        return repository.findById(id).orElseThrow {
            ProtocolReviewRequestNotFoundException("ProtocolReviewRequest with id $id not found")
        }
    }

    override fun create(info: ProtocolReviewRequestInfo): ProtocolReviewRequest {
        if (repository.existsByEditRecordAndState(info.protocolEditRecord, RequestState.PENDING)) {
            throw DuplicateRequestException(
                "ProtocolReviewRequest for protocol with " +
                    "id ${info.protocolEditRecord.protocol.id} is already exists"
            )
        }
        val request = factory.create(info)
        return repository.save(request)
    }

    override fun cancel(id: Long): ProtocolReviewRequest {
        val approachRequest = get(id)
        if (approachRequest.state != RequestState.PENDING) {
            throw RequestImmutableStateException(
                "Can't change state of ${approachRequest.state} " +
                    "ProtocolReviewRequest to ${RequestState.CANCELED}"
            )
        }
        factory.changeState(approachRequest, RequestState.CANCELED)
        return repository.save(approachRequest)
    }

    override fun addReview(id: Long, review: Review): ProtocolReviewRequest {
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
}
