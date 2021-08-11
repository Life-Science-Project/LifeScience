package com.jetbrains.life_science.controller.protocol.published

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.protocol.published.view.PublicProtocolView
import com.jetbrains.life_science.controller.section.view.SectionShortView
import com.jetbrains.life_science.controller.user.view.UserShortView
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/protocol/public_protocol_data.sql"
)
internal class PublicProtocolControllerTest : ApiTest() {

    private val path = "/api/approaches/public"

    /**
     * Test should return public protocol view
     */
    @Test
    fun `get public protocol test`() {
        val expectedView = PublicProtocolView(
            id = 1,
            name = "first published",
            approach = ApproachShortView(
                id = 1,
                name = "approach 1",
                tags = emptyList(),
                creationDate = timeOf(2020, 12, 17)
            ),
            sections = listOf(
                SectionShortView(id = 1, name = "section")
            ),
            coAuthors = listOf(
                UserShortView(id = 1, fullName = "Alex"),
                UserShortView(id = 2, fullName = "Ben")
            )
        )

        val protocol = getView<PublicProtocolView>(makePath(1, 1))

        assertEquals(expectedView, protocol)
    }

    /**
     * Should return 404_007 code
     */
    @Test
    fun `get not existent protocol test`() {
        val request = getRequest(makePath(1, 199))

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_007, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * Should return 404_007 code
     */
    @Test
    fun `get existent protocol by wrong approach id test`() {
        val request = getRequest(makePath(199, 1))

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_007, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    private fun makePath(approachId: Long, protocolId: Long): String {
        return "$path/$approachId/protocols/$protocolId"
    }
}
