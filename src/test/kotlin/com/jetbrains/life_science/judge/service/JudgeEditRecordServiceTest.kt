package com.jetbrains.life_science.judge.service

import com.jetbrains.life_science.exception.judge.RequestJudgeWrongStateException
import com.jetbrains.life_science.exception.not_found.ApproachReviewRequestNotFoundException
import com.jetbrains.life_science.judge.service.handler.JudgeEditRecordServiceTestHandler
import com.jetbrains.life_science.review.request.entity.RequestState
import com.jetbrains.life_science.review.request.service.editRecord.ApproachReviewRequestService
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
    "/scripts/judge/edit_record/approach_review_request_data.sql"
)
@Transactional
internal class JudgeEditRecordServiceTest {

    @MockBean
    lateinit var eventHandler: JudgeEditRecordServiceTestHandler

    @Autowired
    lateinit var service: JudgeEditService

    @Autowired
    lateinit var approachRequestService: ApproachReviewRequestService

    @Test
    fun `judge with not enough reviews`() {
        // Prepare
        val approachRequest = approachRequestService.get(4)

        // Action
        service.judgeApproachEdit(approachRequest)

        // Assert
        val newRequest = approachRequestService.get(4)
        assertEquals(approachRequest.editRecord.id, newRequest.editRecord.id)
        assertEquals(approachRequest.state, newRequest.state)
    }

    @Test
    fun `reject public approach request`() {
        // Prepare
        val request = approachRequestService.get(3)

        // Action
        service.judgeApproachEdit(request)

        // Assert
        val newRequest = approachRequestService.get(3)
        assertEquals(RequestState.CANCELED, newRequest.state)
        verify(eventHandler, times(1))
            .listenApproachReject(any())
    }

    @Test
    fun `approve public approach request`() {
        // Prepare
        val request = approachRequestService.get(2)

        // Action
        service.judgeApproachEdit(request)

        // Assert
        assertThrows<ApproachReviewRequestNotFoundException> {
            approachRequestService.get(2)
        }
        verify(eventHandler, times(1))
            .listenApproachApprove(any())
    }

    @Test
    fun `judge canceled approach request`() {
        // Prepare
        val request = approachRequestService.get(5)

        // Action & Assert
        assertThrows<RequestJudgeWrongStateException> {
            service.judgeApproachEdit(request)
        }
    }
}