package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.exception.not_found.PublishApproachRequestNotFoundException
import com.jetbrains.life_science.exception.request.DuplicateRequestException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.review.request.entity.PublishApproachRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.maker.makePublishApproachRequestInfo
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestService
import com.jetbrains.life_science.review.response.entity.Review
import com.jetbrains.life_science.review.response.entity.ReviewResolution
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import com.jetbrains.life_science.util.UTCZone
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
@Sql("/scripts/initial_data.sql", "/scripts/review/request/publish_approach_request_data.sql")
@Transactional
class PublishApproachRequestServiceTest {

    @Autowired
    lateinit var service: PublishApproachRequestService

    @Autowired
    lateinit var credentialsService: CredentialsService

    /**
     * Should return existing publish approach request
     */
    @Test
    fun `get existing publish approach request`() {
        // Prepare data
        val expectedId = 1L
        val expectedApproachId = 1L
        val expectedState = RequestState.PENDING
        val expectedEditorId = 1L

        // Action
        val publishApproachRequest = service.get(expectedId)

        // Assert
        assertEquals(publishApproachRequest.id, expectedId)
        assertEquals(publishApproachRequest.approach.id, expectedApproachId)
        assertEquals(publishApproachRequest.state, expectedState)
        assertEquals(publishApproachRequest.editor.id, expectedEditorId)
    }

    /**
     * Should throw PublishApproachRequestNotFoundException
     */
    @Test
    fun `get non-existing publish approach request`() {
        // Prepare data
        val expectedId = 239L

        // Action & Assert
        assertThrows<PublishApproachRequestNotFoundException> {
            service.get(expectedId)
        }
    }

    /**
     * Should create new publish approach request
     */
    @Test
    fun `create new publish approach request`() {
        // Prepare data
        val approachOwner = credentialsService.getById(1L)
        val editor = credentialsService.getById(3L)
        val approach = createDraftApproach(2L, "second approach", approachOwner)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val info = makePublishApproachRequestInfo(
            id = 4L,
            date = creationLocalDateTime,
            editor = editor,
            approach = approach
        )
        val expectedState = RequestState.PENDING

        // Action
        service.create(info)
        val publishApproachRequest = service.get(info.id)

        // Assert
        assertEquals(info.id, publishApproachRequest.id)
        assertEquals(editor.id, publishApproachRequest.editor.id)
        assertEquals(approach.id, publishApproachRequest.approach.id)
        assertEquals(expectedState, publishApproachRequest.state)
    }

    /**
     * Should throw DuplicateRequestException, because of existing
     * not canceled request.
     */
    @Test
    fun `create duplicate publish approach request`() {
        // Prepare data
        val approachOwner = credentialsService.getById(1L)
        val editor = credentialsService.getById(3L)
        val approach = createDraftApproach(1L, "first approach", approachOwner)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val info = makePublishApproachRequestInfo(
            id = 3L,
            date = creationLocalDateTime,
            editor = editor,
            approach = approach
        )

        // Action & Assert
        assertThrows<DuplicateRequestException> {
            service.create(info)
        }
    }

    /**
     * Should delete existing publish approach request
     */
    @Test
    fun `delete existing pending publish approach request`() {
        // Prepare data
        val publishApproachId = 2L

        // Action
        service.delete(publishApproachId)

        // Assert
        assertThrows<PublishApproachRequestNotFoundException> {
            service.get(publishApproachId)
        }
    }

    /**
     * Should throw PublishApproachRequestNotFoundException
     */
    @Test
    fun `delete non-existing publish approach request`() {
        // Prepare data
        val publishApproachId = 239L

        // Action & Assert
        assertThrows<PublishApproachRequestNotFoundException> {
            service.delete(publishApproachId)
        }
    }

    /**
     * Should cancel existing publish approach request
     */
    @Test
    fun `cancel existing pending publish approach request`() {
        // Prepare data
        val publishApproachId = 1L
        val expectedState = RequestState.CANCELED

        // Action
        val prevPublishApproachRequest = service.get(publishApproachId)
        service.cancel(publishApproachId)
        val publishApproachRequest = service.get(publishApproachId)

        // Assert
        assertEquals(prevPublishApproachRequest.id, publishApproachRequest.id)
        assertEquals(prevPublishApproachRequest.editor.id, publishApproachRequest.editor.id)
        assertEquals(prevPublishApproachRequest.approach.id, publishApproachRequest.approach.id)
        assertEquals(prevPublishApproachRequest.date, publishApproachRequest.date)
        assertEquals(expectedState, publishApproachRequest.state)
    }

    /**
     * Should throw PublishApproachRequestNotFoundException
     */
    @Test
    fun `cancel non-existing publish approach request`() {
        // Prepare data
        val publishApproachId = 239L

        // Action & Assert
        assertThrows<PublishApproachRequestNotFoundException> {
            service.cancel(publishApproachId)
        }
    }

    /**
     * Should throw RequestImmutableStateException
     */
    @Test
    fun `cancel existing publish approach request with canceled state`() {
        // Prepare data
        val canceledPublishApproachId = 2L

        // Action & Assert
        assertThrows<RequestImmutableStateException> {
            service.cancel(canceledPublishApproachId)
        }
    }

    /**
     * Should add review to publish approach request
     */
    @Test
    fun `add review to existing publish approach request`() {
        // Prepare data
        val publishApproachId = 1L
        val reviewer = credentialsService.getById(3L)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 55, 47)
        val review = createReview(2, creationLocalDateTime, "second review", ReviewResolution.APPROVE, reviewer)

        // Action
        service.addReview(publishApproachId, review)
        val publishApproachRequest = service.get(publishApproachId)

        // Assert
        assertEquals(publishApproachId, publishApproachRequest.id)
        assertContainsReview(publishApproachRequest, review.id)
    }

    /**
     * Should throw PublishApproachRequestNotFoundException
     */
    @Test
    fun `add review to non-existing publish approach request`() {
        // Prepare data
        val publishApproachId = 239L
        val reviewer = credentialsService.getById(3L)
        val review = createReview(
            3, LocalDateTime.now(UTCZone),
            "third review", ReviewResolution.APPROVE, reviewer
        )

        // Action & Assert
        assertThrows<PublishApproachRequestNotFoundException> {
            service.addReview(publishApproachId, review)
        }
    }

    private fun assertContainsReview(publishApproachRequest: PublishApproachRequest, reviewId: Long) {
        assertTrue(publishApproachRequest.reviews.any { it.id == reviewId })
    }

    private fun createDraftApproach(id: Long, name: String, owner: Credentials) =
        DraftApproach(
            id = id,
            name = name,
            aliases = mutableListOf(),
            owner = owner,
            tags = mutableListOf(),
            sections = mutableListOf(),
            categories = mutableListOf(),
            creationDate = LocalDateTime.now(UTCZone),
            participants = mutableListOf(owner)
        )

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
