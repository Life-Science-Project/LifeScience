package com.jetbrains.life_science.review.request.service

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.exception.not_found.PublishProtocolRequestNotFoundException
import com.jetbrains.life_science.exception.request.RequestImmutableStateException
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.exception.request.DuplicateRequestException
import com.jetbrains.life_science.review.request.entity.PublishProtocolRequest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.maker.makePublishProtocolRequestInfo
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestService
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
import java.time.LocalDateTime

import javax.transaction.Transactional

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/review/request/publish_protocol_request_data.sql")
@Transactional
class PublishProtocolRequestServiceTest {

    @Autowired
    lateinit var service: PublishProtocolRequestService

    @Autowired
    lateinit var credentialsService: CredentialsService

    /**
     * Should return existing publish protocol request
     */
    @Test
    fun `get existing publish protocol request`() {
        // Prepare data
        val expectedId = 1L
        val expectedProtocolId = 1L
        val expectedState = RequestState.PENDING
        val expectedEditorId = 1L

        // Action
        val publishProtocolRequest = service.get(expectedId)

        // Assert
        assertEquals(publishProtocolRequest.id, expectedId)
        assertEquals(publishProtocolRequest.protocol.id, expectedProtocolId)
        assertEquals(publishProtocolRequest.state, expectedState)
        assertEquals(publishProtocolRequest.editor.id, expectedEditorId)
    }

    /**
     * Should throw PublishProtocolRequestNotFoundException
     */
    @Test
    fun `get non-existing publish protocol request`() {
        // Prepare data
        val expectedId = 239L

        // Action & Assert
        assertThrows<PublishProtocolRequestNotFoundException> {
            service.get(expectedId)
        }
    }

    /**
     * Should create new publish protocol request
     */
    @Test
    fun `create new publish protocol request`() {
        // Prepare data
        val owner = credentialsService.getById(1L)
        val editor = credentialsService.getById(3L)
        val protocolCreationLocalDateTime =
            LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val approachCreationLocalDateTime = LocalDateTime.of(2020, 12, 17, 0, 0)
        val publicApproach = createPublicApproach(1L, "approach 1", owner, approachCreationLocalDateTime)
        val protocol = createDraftProtocol(2L, "second protocol", publicApproach, owner)
        val info = makePublishProtocolRequestInfo(
            id = 4L,
            date = protocolCreationLocalDateTime,
            editor = editor,
            protocol = protocol
        )
        val expectedState = RequestState.PENDING

        // Action
        service.create(info)
        val publishProtocolRequest = service.get(info.id)

        // Assert
        assertEquals(info.id, publishProtocolRequest.id)
        assertEquals(editor.id, publishProtocolRequest.editor.id)
        assertEquals(protocol.id, publishProtocolRequest.protocol.id)
        assertEquals(expectedState, publishProtocolRequest.state)
    }

    /**
     * Should throw DuplicateRequestException, because of existing
     * not canceled request.
     */
    @Test
    fun `create duplicate publish protocol request`() {
        // Prepare data
        val owner = credentialsService.getById(1L)
        val editor = credentialsService.getById(3L)
        val protocolCreationLocalDateTime =
            LocalDateTime.of(2021, 5, 21, 12, 53, 47)
        val approachCreationLocalDateTime = LocalDateTime.of(2020, 12, 17, 0, 0)
        val publicApproach = createPublicApproach(1L, "approach 1", owner, approachCreationLocalDateTime)
        val protocol = createDraftProtocol(1L, "first protocol", publicApproach, owner)
        val info = makePublishProtocolRequestInfo(
            id = 3L,
            date = protocolCreationLocalDateTime,
            editor = editor,
            protocol = protocol
        )

        // Action & Assert
        assertThrows<DuplicateRequestException> {
            service.create(info)
        }
    }

    /**
     * Should delete existing publish protocol request
     */
    @Test
    fun `delete existing pending publish protocol request`() {
        // Prepare data
        val publishProtocolId = 2L

        // Action
        service.delete(publishProtocolId)

        // Assert
        assertThrows<PublishProtocolRequestNotFoundException> {
            service.get(publishProtocolId)
        }
    }

    /**
     * Should throw PublishProtocolRequestNotFoundException
     */
    @Test
    fun `delete non-existing publish protocol request`() {
        // Prepare data
        val publishProtocolId = 239L

        // Action & Assert
        assertThrows<PublishProtocolRequestNotFoundException> {
            service.delete(publishProtocolId)
        }
    }

    /**
     * Should cancel existing publish protocol request
     */
    @Test
    fun `cancel existing pending publish protocol request`() {
        // Prepare data
        val publishProtocolId = 1L
        val expectedState = RequestState.CANCELED

        // Action
        val prevPublishProtocolRequest = service.get(publishProtocolId)
        service.cancel(publishProtocolId)
        val publishProtocolRequest = service.get(publishProtocolId)

        // Assert
        assertEquals(prevPublishProtocolRequest.id, publishProtocolRequest.id)
        assertEquals(prevPublishProtocolRequest.editor.id, publishProtocolRequest.editor.id)
        assertEquals(prevPublishProtocolRequest.protocol.id, publishProtocolRequest.protocol.id)
        assertEquals(prevPublishProtocolRequest.date, publishProtocolRequest.date)
        assertEquals(expectedState, publishProtocolRequest.state)
    }

    /**
     * Should throw PublishProtocolRequestNotFoundException
     */
    @Test
    fun `cancel non-existing publish protocol request`() {
        // Prepare data
        val publishProtocolId = 239L

        // Action & Assert
        assertThrows<PublishProtocolRequestNotFoundException> {
            service.cancel(publishProtocolId)
        }
    }

    /**
     * Should throw RequestImmutableStateException
     */
    @Test
    fun `cancel existing publish protocol request with approved or canceled state`() {
        // Prepare data
        val canceledPublishProtocolId = 2L

        // Action & Assert
        assertThrows<RequestImmutableStateException> {
            service.cancel(canceledPublishProtocolId)
        }
    }

    /**
     * Should add review to publish protocol request
     */
    @Test
    fun `add review to existing publish protocol request`() {
        // Prepare data
        val publishProtocolId = 1L
        val reviewer = credentialsService.getById(3L)
        val creationLocalDateTime = LocalDateTime.of(2021, 5, 21, 12, 55, 47)
        val review = createReview(2, creationLocalDateTime, "second review", ReviewResolution.APPROVE, reviewer)

        // Action
        service.addReview(publishProtocolId, review)
        val publishProtocolRequest = service.get(publishProtocolId)

        // Assert
        assertEquals(publishProtocolId, publishProtocolRequest.id)
        assertContainsReview(publishProtocolRequest, review.id)
    }

    /**
     * Should throw PublishProtocolRequestNotFoundException
     */
    @Test
    fun `add review to non-existing publish protocol request`() {
        // Prepare data
        val publishProtocolId = 239L
        val reviewer = credentialsService.getById(3L)
        val review = createReview(
            3, LocalDateTime.now(UTCZone),
            "third review", ReviewResolution.APPROVE, reviewer
        )

        // Action & Assert
        assertThrows<PublishProtocolRequestNotFoundException> {
            service.addReview(publishProtocolId, review)
        }
    }

    private fun assertContainsReview(publishProtocolRequest: PublishProtocolRequest, reviewId: Long) {
        assertTrue(publishProtocolRequest.reviews.any { it.id == reviewId })
    }

    private fun createDraftProtocol(id: Long, name: String, approach: PublicApproach, owner: Credentials) =
        DraftProtocol(
            id = id,
            name = name,
            owner = owner,
            approach = approach,
            sections = mutableListOf(),
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

    private fun createPublicApproach(id: Long, name: String, owner: Credentials, date: LocalDateTime) = PublicApproach(
        id = id,
        name = name,
        aliases = mutableListOf(),
        sections = mutableListOf(),
        tags = mutableListOf(),
        owner = owner,
        categories = mutableListOf(),
        creationDate = date,
        coAuthors = mutableListOf(),
        protocols = mutableListOf()
    )
}
