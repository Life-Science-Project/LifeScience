package com.jetbrains.life_science.controller.protocol.draft

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.protocol.draft.dto.DraftProtocolAddParticipantDTO
import com.jetbrains.life_science.controller.protocol.draft.dto.DraftProtocolDTO
import com.jetbrains.life_science.controller.protocol.draft.view.DraftProtocolView
import com.jetbrains.life_science.controller.user.view.UserShortView
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/protocol/draft_protocol_data.sql"
)
internal class DraftProtocolControllerTest : ApiTest() {

    private val path = "/api/protocols/draft"

    /**
     * Test should return draft protocol view
     */
    @Test
    fun `get draft protocol test`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        val expectedView = DraftProtocolView(
            id = 1,
            name = "draft_protocol_test",
            sections = emptyList(),
            approach = ApproachShortView(1, "approach 1", timeOf(2020, 12, 17), emptyList()),
            participants = listOf(
                UserShortView(id = 1, fullName = "Alex"),
                UserShortView(id = 2, fullName = "Ben")
            )
        )

        // Action
        val protocol = getViewAuthorized<DraftProtocolView>(makePath(1), loginAccessToken)

        // Assert
        Assertions.assertEquals(expectedView, protocol)
    }

    /**
     * An attempt to get non-existing draft protocol.
     * Expected 404 http code and 404_007 system code.
     */
    @Test
    fun `get non-existent protocol test`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        val request = getAuthorized(makePath(239), loginAccessToken)

        // Action
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        Assertions.assertEquals(404_007, exceptionView.systemCode)
    }

    /**
     * Test should delete existing draft protocol
     */
    @Test
    fun `delete draft protocol test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")

        deleteAuthorized(makePath(1), loginAccessToken)

        val request = getAuthorized(makePath(1), loginAccessToken)
        val exceptionView = getApiExceptionView(404, request)

        Assertions.assertEquals(404_007, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * Should return ApiExceptionView
     *
     * Expected 404 http code and 404_007 system code result
     */
    @Test
    fun `delete not existent protocol test`() {
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        val request = deleteRequestAuthorized(makePath(199), loginAccessToken)

        val exceptionView = getApiExceptionView(404, request)

        Assertions.assertEquals(404_007, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * Successful draft protocol creation.
     */
    @Test
    fun `create protocol with base sections test`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        val dto = DraftProtocolDTO(
            name = "test protocol",
            approachId = 1
        )

        // Action
        val created = postAuthorized<DraftProtocolView>(path, dto, loginAccessToken)
        val protocol = getViewAuthorized<DraftProtocolView>(makePath(created.id), loginAccessToken)

        // Assert
        val expectedView = DraftProtocolView(
            id = protocol.id,
            name = "test protocol",
            approach = ApproachShortView(1, "approach 1", timeOf(2020, 12, 17), emptyList()),
            sections = listOf(),
            participants = listOf(UserShortView(id = 1, fullName = "Alex"))
        )
        Assertions.assertEquals(expectedView, protocol)
    }

    /**
     * An attempt to create protocol with wrong approach id.
     * Expected 404 http code and 404_003 system code.
     */
    @Test
    fun `create protocol with wrong initial approach id`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        val dto = DraftProtocolDTO(
            name = "test protocol",
            approachId = 239
        )
        val request = postRequestAuthorized(path, dto, loginAccessToken)

        // Action
        val exceptionView = getApiExceptionView(404, request)

        // Assert
        Assertions.assertEquals(404_003, exceptionView.systemCode)
    }

    /**
     * Successful adding user to protocol participants.
     */
    @Test
    fun `add participant test`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        val dto = DraftProtocolAddParticipantDTO("regular@gmail.ru")
        val expectedView = DraftProtocolView(
            id = 1,
            name = "draft_protocol_test",
            sections = emptyList(),
            participants = listOf(
                UserShortView(id = 1, fullName = "Alex"),
                UserShortView(id = 2, fullName = "Ben"),
                UserShortView(id = 4, fullName = "Regular")
            ),
            approach = ApproachShortView(1, "approach 1", timeOf(2020, 12, 17), emptyList())
        )

        // Action
        postRequestAuthorized(makePath("1/participants"), dto, loginAccessToken)
        val protocol = getViewAuthorized<DraftProtocolView>(makePath(1), loginAccessToken)

        // Assert
        Assertions.assertEquals(expectedView, protocol)
    }

    /**
     * Try to add new user to participants by user without permission.
     *
     * Expected 403 http code and 403_000 system code result.
     */
    @Test
    fun `add user to participants by regular user`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("simple@gmail.ru", "user123")
        val dto = DraftProtocolAddParticipantDTO("admin@gmail.ru")

        // Action
        val request = postRequestAuthorized(makePath("1/participants"), dto, loginAccessToken)
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        Assertions.assertEquals(403_000, exceptionView.systemCode)
    }

    /**
     * Try to add new user to participants by anonymous user
     *
     * Expected 403 http code and 403_000 system code result.
     */
    @Test
    fun `add to participants by anonymous user`() {
        // Prepare data
        val dto = DraftProtocolAddParticipantDTO("email3@email.ru")

        // Action
        val request = postRequest(makePath("1/participants"), dto)
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        Assertions.assertEquals(403_000, exceptionView.systemCode)
    }

    /**
     * Successful delete user from protocol participants.
     */
    @Test
    fun `delete participant test`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("email@email.ru", "password")
        val expectedView = DraftProtocolView(
            id = 1,
            name = "draft_protocol_test",
            approach = ApproachShortView(1, "approach 1", timeOf(2020, 12, 17), emptyList()),
            sections = emptyList(),
            participants = listOf(
                UserShortView(id = 1, fullName = "Alex")
            )
        )

        // Action
        deleteAuthorized(makePath("1/participants/2"), loginAccessToken)
        val protocol = getViewAuthorized<DraftProtocolView>(makePath(1), loginAccessToken)

        // Assert
        Assertions.assertEquals(expectedView, protocol)
    }

    /**
     * User without permission try to delete user from participants.
     *
     * Expected 403 http code and 403_000 system code result.
     */
    @Test
    fun `delete participant by regular user test`() {
        // Prepare data
        val loginAccessToken = loginAccessToken("simple@gmail.ru", "user123")

        // Action
        val request = deleteRequestAuthorized(makePath("1/participants/2"), loginAccessToken)
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        Assertions.assertEquals(403_000, exceptionView.systemCode)
    }

    /**
     * Anonymous user try to delete user from participants.
     *
     * Expected 403 http code and 403_000 system code result.
     */
    @Test
    fun `delete participant by anonymous user test`() {
        // Prepare data
        val request = deleteRequest(makePath("1/participants/2"))

        // Action
        val exceptionView = getApiExceptionView(403, request)

        // Assert
        Assertions.assertEquals(403_000, exceptionView.systemCode)
    }

    private fun makePath(addition: Any): String {
        return "$path/$addition"
    }
}
