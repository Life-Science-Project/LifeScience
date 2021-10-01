package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.edit_record.service.ProtocolEditRecordService
import com.jetbrains.life_science.exception.not_found.ProtocolReviewRequestNotFoundException
import com.jetbrains.life_science.exception.request.DuplicateRequestException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.request.entity.ProtocolReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.editRecord.ProtocolReviewRequestService
import com.jetbrains.life_science.review.request.service.maker.makeProtocolReviewRequestInfo
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/review/request/protocol_review_request_data.sql")
@Transactional
class ProtocolReviewRequestServiceTest {

    @Autowired
    lateinit var service: ProtocolReviewRequestService

    @Autowired
    lateinit var credentialsService: CredentialsService

    @Autowired
    lateinit var editRecordService: ProtocolEditRecordService

    /**
     * Should return existing protocol request
     */
    @Test
    fun `get existing protocol review request`() {
        // Prepare data
        val expectedId = 1L
        val expectedEditRecordId = 1L
        val expectedState = RequestState.PENDING
        val expectedEditorId = 1L

        // Action
        val protocolReviewRequest = service.get(expectedId)

        // Assert
        assertEquals(protocolReviewRequest.id, expectedId)
        assertEquals(protocolReviewRequest.editRecord.id, expectedEditRecordId)
        assertEquals(protocolReviewRequest.state, expectedState)
        assertEquals(protocolReviewRequest.editor.id, expectedEditorId)
    }

    /**
     * Should throw ProtocolReviewRequestNotFoundException
     */
    @Test
    fun `get non-existing protocol review request`() {
        // Prepare data
        val expectedId = 239L

        // Action & Assert
        assertThrows<ProtocolReviewRequestNotFoundException> {
            service.get(expectedId)
        }
    }

    /**
     * Should create new protocol review request
     */
    @Test
    fun `create new protocol review request`() {
        // Prepare data
        val editor = credentialsService.getById(1L)
        val editRecord = editRecordService.get(2L)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val info = makeProtocolReviewRequestInfo(
            id = 3L,
            date = creationLocalDateTime,
            editor = editor,
            editRecord = editRecord
        )
        val expectedState = RequestState.PENDING

        // Action
        service.create(info)
        val protocolReviewRequest = service.get(info.id)

        // Assert
        assertEquals(info.id, protocolReviewRequest.id)
        assertEquals(editor.id, protocolReviewRequest.editor.id)
        assertEquals(editRecord.id, protocolReviewRequest.editRecord.id)
        assertEquals(expectedState, protocolReviewRequest.state)
    }

    /**
     * Should throw DuplicateRequestException, because of existing
     * not canceled request.
     */
    @Test
    fun `create duplicate protocol review request`() {
        // Prepare data
        val editor = credentialsService.getById(1L)
        val editRecord = editRecordService.get(1L)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val info = makeProtocolReviewRequestInfo(
            id = 2L,
            date = creationLocalDateTime,
            editor = editor,
            editRecord = editRecord
        )

        // Action & Assert
        assertThrows<DuplicateRequestException> {
            service.create(info)
        }
    }

    /**
     * Should delete existing protocol review request
     */
    @Test
    fun `delete existing pending protocol review request`() {
        // Prepare data
        val requestId = 1L

        // Action
        service.delete(requestId)

        // Assert
        assertThrows<ProtocolReviewRequestNotFoundException> {
            service.get(requestId)
        }
    }

    /**
     * Should throw ProtocolReviewRequestNotFoundException
     */
    @Test
    fun `delete non-existing protocol review request`() {
        // Prepare data
        val requestId = 239L

        // Action & Assert
        assertThrows<ProtocolReviewRequestNotFoundException> {
            service.delete(requestId)
        }
    }

    /**
     * Should cancel existing protocol review request
     */
    @Test
    fun `cancel existing pending protocol review request`() {
        // Prepare data
        val requestId = 1L
        val expectedState = RequestState.CANCELED

        // Action
        val prevProtocolReviewRequest = service.get(requestId)
        service.cancel(requestId)
        val protocolReviewRequest = service.get(requestId)

        // Assert
        assertEquals(prevProtocolReviewRequest.id, protocolReviewRequest.id)
        assertEquals(prevProtocolReviewRequest.editor.id, protocolReviewRequest.editor.id)
        assertEquals(prevProtocolReviewRequest.editRecord.id, protocolReviewRequest.editRecord.id)
        assertEquals(prevProtocolReviewRequest.date, protocolReviewRequest.date)
        assertEquals(expectedState, protocolReviewRequest.state)
    }

    /**
     * Should throw ProtocolReviewRequestNotFoundException
     */
    @Test
    fun `cancel non-existing protocol review request`() {
        // Prepare data
        val requestId = 239L

        // Action & Assert
        assertThrows<ProtocolReviewRequestNotFoundException> {
            service.cancel(requestId)
        }
    }

    /**
     * Should throw RequestImmutableStateException
     */
    @Test
    fun `cancel existing protocol review request with canceled state`() {
        // Prepare data
        val requestId = 2L

        // Action & Assert
        assertThrows<RequestImmutableStateException> {
            service.cancel(requestId)
        }
    }

    /**
     * Should add review to protocol review request
     */
    @Test
    fun `add review to existing protocol review request`() {
        // Prepare data
        val requestId = 1L
        val reviewer = credentialsService.getById(3L)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 55, 47)
        val review = createReview(4, creationLocalDateTime, "second review", ReviewResolution.APPROVE, reviewer)

        // Action
        service.addReview(requestId, review)
        val protocolReviewRequest = service.get(requestId)

        // Assert
        assertEquals(requestId, protocolReviewRequest.id)
        assertContainsReview(protocolReviewRequest, review.id)
    }

    /**
     * Should throw ProtocolReviewRequestNotFoundException
     */
    @Test
    fun `add review to non-existing protocol review request`() {
        // Prepare data
        val publishApproachId = 239L
        val reviewer = credentialsService.getById(3L)
        val review = createReview(3, LocalDateTime.now(), "third review", ReviewResolution.APPROVE, reviewer)

        // Action & Assert
        assertThrows<ProtocolReviewRequestNotFoundException> {
            service.addReview(publishApproachId, review)
        }
    }

    private fun assertContainsReview(protocolReviewRequest: ProtocolReviewRequest, reviewId: Long) {
        assertTrue(protocolReviewRequest.reviews.any { it.id == reviewId })
    }

    private fun createReview(
        id: Long,
        date: LocalDateTime,
        comment: String,
        resolution: ReviewResolution,
        reviewer: Credentials
    ) = Review(
        id = id,
        date = date,
        comment = comment,
        resolution = resolution,
        reviewer = reviewer
    )
}
