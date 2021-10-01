package com.jetbrains.life_science.controller.approach.published

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.protocol.view.ProtocolShortView
import com.jetbrains.life_science.controller.approach.published.view.PublicApproachView
import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.user.view.UserShortView
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql

@Sql(
    "/scripts/initial_data.sql",
    "/scripts/approach/public_approach_data.sql"
)
internal class PublicApproachControllerTest : ApiTest() {

    private val path = "/api/approaches/public"

    /**
     * Test should return public approach view
     */
    @Test
    fun `get public approach test`() {
        val expectedView = PublicApproachView(
            id = 1,
            name = "approach 1",
            categories = listOf(
                CategoryShortView(1, "catalog 1", timeOf(2020, 9, 17)),
            ),
            sections = emptyList(),
            coAuthors = listOf(
                UserShortView(id = 1, fullName = "Alex"),
                UserShortView(id = 2, fullName = "Ben")
            ),
            protocols = listOf(
                ProtocolShortView(id = 1, name = "first published")
            )
        )

        val approach = getView<PublicApproachView>(makePath(1))

        assertEquals(expectedView, approach)
    }

    /**
     * Should return 404_003 code
     */
    @Test
    fun `get not existent approach test`() {
        val request = getRequest(makePath(199))

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_003, exceptionView.systemCode)
        Assertions.assertTrue(exceptionView.arguments.isEmpty())
    }

    private fun makePath(addition: Any): String {
        return "$path/$addition"
    }
}
