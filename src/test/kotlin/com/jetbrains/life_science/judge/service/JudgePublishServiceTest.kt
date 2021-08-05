package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.container.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.container.protocol.search.service.ProtocolSearchUnitService
import com.jetbrains.life_science.exception.judge.RequestJudgeWrongStateException
import com.jetbrains.life_science.exception.not_found.PublishApproachRequestNotFoundException
import com.jetbrains.life_science.exception.not_found.PublishProtocolRequestNotFoundException
import com.jetbrains.life_science.judge.service.handler.JudgePublishServiceTestHandler
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.publish.PublishApproachRequestService
import com.jetbrains.life_science.review.request.service.publish.PublishProtocolRequestService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql(
    "/scripts/initial_data.sql",
    "/scripts/judge/publish/publish_approach_request_data.sql",
    "/scripts/judge/publish/publish_protocol_request_data.sql"
)
@Transactional
internal class JudgePublishServiceTest {

    @MockBean
    lateinit var protocolSearchUnitService: ProtocolSearchUnitService

    @MockBean
    lateinit var approachSearchUnitService: ApproachSearchUnitService

    @MockBean
    lateinit var eventHandler: JudgePublishServiceTestHandler

    @Autowired
    lateinit var service: JudgePublishService

    @Autowired
    lateinit var approachRequestService: PublishApproachRequestService

    @Autowired
    lateinit var protocolRequestService: PublishProtocolRequestService

    /**
     * Should do nothing
     */
    @Test
    fun `judge approach publish request with not enough reviews`() {
        // Prepare
        val approachRequest = approachRequestService.get(3)

        // Action
        service.judgeApproachPublish(approachRequest)

        // Assert
        val newRequest = approachRequestService.get(3)
        assertEquals(approachRequest.approach.id, newRequest.approach.id)
        assertEquals(approachRequest.state, newRequest.state)
    }

    /**
     * Should change request state and create reject event
     */
    @Test
    fun `reject public approach request`() {
        // Prepare
        val request = approachRequestService.get(2)

        // Action
        service.judgeApproachPublish(request)

        // Assert
        val newRequest = approachRequestService.get(2)
        assertEquals(RequestState.CANCELED, newRequest.state)
        verify(eventHandler, times(1))
            .listenApproachReject(any())
    }

    /**
     * Should delete request, create public approach and approve event
     */
    @Test
    fun `approve public approach request`() {
        // Prepare
        val request = approachRequestService.get(1)

        // Action
        service.judgeApproachPublish(request)

        // Assert
        assertThrows<PublishApproachRequestNotFoundException> {
            approachRequestService.get(1)
        }
        verify(eventHandler, times(1))
            .listenApproachApprove(any())
    }

    /**
     * Should throw RequestJudgeWrongStateException
     */
    @Test
    fun `judge canceled approach request`() {
        // Prepare
        val request = approachRequestService.get(4)

        // Action & Assert
        assertThrows<RequestJudgeWrongStateException> {
            service.judgeApproachPublish(request)
        }
    }

    /**
     * Should change request state and create reject event
     */
    @Test
    fun `reject public protocol request`() {
        // Prepare
        val request = protocolRequestService.get(6)

        // Action
        service.judgeProtocolPublish(request)

        // Assert
        val newRequest = protocolRequestService.get(6)
        assertEquals(RequestState.CANCELED, newRequest.state)
        verify(eventHandler, times(1))
            .listenProtocolReject(any())
    }

    /**
     * Should delete request, create public approach and approve event
     */
    @Test
    fun `approve public protocol request`() {
        // Prepare
        val request = protocolRequestService.get(5)

        // Action
        service.judgeProtocolPublish(request)

        // Assert
        assertThrows<PublishProtocolRequestNotFoundException> {
            protocolRequestService.get(1)
        }
        verify(eventHandler, times(1))
            .listenProtocolApprove(any())
    }

    /**
     * Should throw RequestJudgeWrongStateException
     */
    @Test
    fun `judge canceled protocol request`() {
        // Prepare
        val request = protocolRequestService.get(8)

        // Action & Assert
        assertThrows<RequestJudgeWrongStateException> {
            service.judgeProtocolPublish(request)
        }
    }
}
