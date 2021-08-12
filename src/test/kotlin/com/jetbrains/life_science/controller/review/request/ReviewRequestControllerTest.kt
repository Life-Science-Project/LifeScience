package com.jetbrains.life_science.controller.review.request

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.repository.PublishApproachRequestRepository
import com.jetbrains.life_science.review.request.repository.PublishProtocolRequestRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/review/request/publish_approach_request_data.sql",
    "/scripts/review/request/publish_protocol_request_data.sql"
)
internal class ReviewRequestControllerTest : ApiTest() {

    private val draftApproachPath = "/api/review/request/approaches/draft"
    private val draftProtocolPath = "/api/review/request/protocols/draft"

    @Autowired
    lateinit var publishApproachRequestRepository: PublishApproachRequestRepository

    @Autowired
    lateinit var publishProtocolRequestRepository: PublishProtocolRequestRepository

    /**
     * Test checks sending to publication draft approach
     */
    @Test
    fun `send to publication draft approach test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        postAuthorized(makeDraftApproachPath("2"), loginAccessToken)

        val request = publishApproachRequestRepository.findAll().filter { it.approach.id == 1L }
        assertTrue(request.size == 2)
    }

    /**
     * Test checks canceling draft approach request
     */
    @Test
    fun `cancel draft approach request test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        patchAuthorized(makeDraftApproachPath("1/1/cancel"), loginAccessToken)

        val request = publishApproachRequestRepository.findById(1).get()
        assertEquals(RequestState.CANCELED, request.state)
    }

    /**
     * Test checks deleting draft approach request
     */
    @Test
    fun `delete draft approach request test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        deleteAuthorized(makeDraftApproachPath("1/2"), loginAccessToken)

        assertThrows<NoSuchElementException> {
            publishApproachRequestRepository.findById(2).get()
        }
    }

    /**
     * Test checks reject to publication by other user
     *
     * Expected 403 http code and 403_000 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `send to publication draft approach by regular user`() {
        val loginAccessToken = loginAccessToken("simple@gmail.ru", "user123")
        val request = postRequestAuthorized(makeDraftApproachPath("1"), loginAccessToken)

        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
    }

    /**
     * Test checks reject to publication by anonymous user
     *
     * Expected 403 http code and 403_000 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `send to publication draft approach by anonymous user`() {
        val request = postRequest(makeDraftApproachPath("1"), null)

        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
    }

    /**
     * Test checks sending to publication draft approach
     */
    @Test
    fun `send to publication draft protocol test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        postAuthorized(makeDraftProtocolPath("2"), loginAccessToken)

        val request = publishProtocolRequestRepository.findAll().filter { it.protocol.id == 1L }
        assertTrue(request.size == 2)
    }

    /**
     * Test checks canceling draft protocol request
     */
    @Test
    fun `cancel draft protocol request test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        patchAuthorized(makeDraftProtocolPath("1/1/cancel"), loginAccessToken)

        val request = publishProtocolRequestRepository.findById(1).get()
        assertEquals(RequestState.CANCELED, request.state)
    }

    /**
     * Test checks deleting draft approach request
     */
    @Test
    fun `delete draft protocol request test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        deleteAuthorized(makeDraftProtocolPath("1/2"), loginAccessToken)

        assertThrows<NoSuchElementException> {
            publishProtocolRequestRepository.findById(2).get()
        }
    }

    /**
     * Test checks reject to publication by other user
     *
     * Expected 403 http code and 403_000 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `send to publication draft protocol by regular user`() {
        val loginAccessToken = loginAccessToken("simple@gmail.ru", "user123")
        val request = postRequestAuthorized(makeDraftProtocolPath("1"), loginAccessToken)

        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
    }

    /**
     * Test checks reject to publication by anonymous user
     *
     * Expected 403 http code and 403_000 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `send to publication draft protocol by anonymous user`() {
        val request = postRequest(makeDraftProtocolPath("1"), null)

        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
    }

    private fun makeDraftApproachPath(addition: Any): String {
        return "$draftApproachPath/$addition"
    }

    private fun makeDraftProtocolPath(addition: Any): String {
        return "$draftProtocolPath/$addition"
    }
}
