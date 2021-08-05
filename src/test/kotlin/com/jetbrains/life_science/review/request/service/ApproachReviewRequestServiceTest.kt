package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.edit_record.service.ApproachEditRecordService
import com.jetbrains.life_science.exception.not_found.ApproachReviewRequestNotFoundException
import com.jetbrains.life_science.exception.request.DuplicateRequestException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.request.entity.ApproachReviewRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.editRecord.ApproachReviewRequestService
import com.jetbrains.life_science.review.request.service.maker.makeApproachReviewRequestInfo
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
@Sql("/scripts/initial_data.sql", "/scripts/review/request/approach_review_request_data.sql")
@Transactional
class ApproachReviewRequestServiceTest {

    @Autowired
    lateinit var service: ApproachReviewRequestService

    @Autowired
    lateinit var credentialsService: CredentialsService

    @Autowired
    lateinit var editRecordService: ApproachEditRecordService

    /**
     * Should return existing approach request
     */
    @Test
    fun `get existing approach review request`() {
        // Prepare data
        val expectedId = 1L
        val expectedEditRecordId = 1L
        val expectedState = RequestState.PENDING
        val expectedEditorId = 1L

        // Action
        val publishApproachRequest = service.get(expectedId)

        // Assert
        assertEquals(publishApproachRequest.id, expectedId)
        assertEquals(publishApproachRequest.editRecord.id, expectedEditRecordId)
        assertEquals(publishApproachRequest.state, expectedState)
        assertEquals(publishApproachRequest.editor.id, expectedEditorId)
    }

    /**
     * Should throw ApproachReviewRequestNotFoundException
     */
    @Test
    fun `get non-existing approach review request`() {
        // Prepare data
        val expectedId = 239L

        // Action & Assert
        assertThrows<ApproachReviewRequestNotFoundException> {
            service.get(expectedId)
        }
    }

    /**
     * Should create new approach review request
     */
    @Test
    fun `create new approach review request`() {
        // Prepare data
        val editor = credentialsService.getById(1L)
        val editRecord = editRecordService.get(2L)
        val expectedId = 4L
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val info = makeApproachReviewRequestInfo(
            date = creationLocalDateTime,
            editor = editor,
            editRecord = editRecord
        )
        val expectedState = RequestState.PENDING

        // Action
        service.create(info)
        val approachReviewRequest = service.get(expectedId)

        // Assert
        assertEquals(expectedId, approachReviewRequest.id)
        assertEquals(editor.id, approachReviewRequest.editor.id)
        assertEquals(editRecord.id, approachReviewRequest.editRecord.id)
        assertEquals(expectedState, approachReviewRequest.state)
    }

    /**
     * Should throw DuplicateRequestException, because of existing
     * not canceled request.
     */
    @Test
    fun `create duplicate approach review request`() {
        // Prepare data
        val editor = credentialsService.getById(1L)
        val editRecord = editRecordService.get(1L)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val info = makeApproachReviewRequestInfo(
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
     * Should delete existing approach review request
     */
    @Test
    fun `delete existing pending approach review request`() {
        // Prepare data
        val requestId = 1L

        // Action
        service.delete(requestId)

        // Assert
        assertThrows<ApproachReviewRequestNotFoundException> {
            service.get(requestId)
        }
    }

    /**
     * Should throw ApproachReviewRequestNotFoundException
     */
    @Test
    fun `delete non-existing approach review request`() {
        // Prepare data
        val requestId = 239L

        // Action & Assert
        assertThrows<ApproachReviewRequestNotFoundException> {
            service.delete(requestId)
        }
    }

    /**
     * Should cancel existing approach review request
     */
    @Test
    fun `cancel existing pending approach review request`() {
        // Prepare data
        val requestId = 1L
        val expectedState = RequestState.CANCELED

        // Action
        val prevApproachReviewRequest = service.get(requestId)
        service.cancel(requestId)
        val approachReviewRequest = service.get(requestId)

        // Assert
        assertEquals(prevApproachReviewRequest.id, approachReviewRequest.id)
        assertEquals(prevApproachReviewRequest.editor.id, approachReviewRequest.editor.id)
        assertEquals(prevApproachReviewRequest.editRecord.id, approachReviewRequest.editRecord.id)
        assertEquals(prevApproachReviewRequest.date, approachReviewRequest.date)
        assertEquals(expectedState, approachReviewRequest.state)
    }

    /**
     * Should throw ApproachReviewRequestNotFoundException
     */
    @Test
    fun `cancel non-existing approach review request`() {
        // Prepare data
        val requestId = 239L

        // Action & Assert
        assertThrows<ApproachReviewRequestNotFoundException> {
            service.cancel(requestId)
        }
    }

    /**
     * Should throw RequestImmutableStateException
     */
    @Test
    fun `cancel existing approach review request with canceled state`() {
        // Prepare data
        val requestId = 2L

        // Action & Assert
        assertThrows<RequestImmutableStateException> {
            service.cancel(requestId)
        }
    }

    /**
     * Should add review to approach review request
     */
    @Test
    fun `add review to existing approach review request`() {
        // Prepare data
        val requestId = 1L
        val reviewer = credentialsService.getById(3L)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 55, 47)
        val review = createReview(2, creationLocalDateTime, "second review", ReviewResolution.APPROVE, reviewer)

        // Action
        service.addReview(requestId, review)
        val approachReviewRequest = service.get(requestId)

        // Assert
        assertEquals(requestId, approachReviewRequest.id)
        assertContainsReview(approachReviewRequest, review.id)
    }

    /**
     * Should throw ApproachReviewRequestNotFoundException
     */
    @Test
    fun `add review to non-existing approach review request`() {
        // Prepare data
        val publishApproachId = 239L
        val reviewer = credentialsService.getById(3L)
        val review = createReview(3, LocalDateTime.now(), "third review", ReviewResolution.APPROVE, reviewer)

        // Action & Assert
        assertThrows<ApproachReviewRequestNotFoundException> {
            service.addReview(publishApproachId, review)
        }
    }

    private fun assertContainsReview(approachReviewRequest: ApproachReviewRequest, reviewId: Long) {
        assertTrue(approachReviewRequest.reviews.any { it.id == reviewId })
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
