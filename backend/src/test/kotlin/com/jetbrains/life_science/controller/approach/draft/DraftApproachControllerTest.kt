package com.jetbrains.life_science.controller.approach.draft

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.approach.draft.dto.DraftApproachCreationDTO
import com.jetbrains.life_science.controller.approach.draft.view.DraftApproachView
import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.user.UserShortView
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDateTime

@Sql(
    "/scripts/users_data.sql",
    "/scripts/categories_initial_data.sql",
    "/scripts/draft_approach_data.sql"
)
internal class DraftApproachControllerTest : ApiTest() {

    private val path = "/api/approaches/draft"

    /**
     * Test should return draft approach view
     */
    @Test
    fun `get draft approach test`() {
        val expectedView = DraftApproachView(
            id = 1,
            name = "approach 1",
            categories = listOf(
                CategoryShortView(1, "catalog 1", timeOf(2020, 9, 17)),
                CategoryShortView(2, "catalog 2", timeOf(2020, 10, 17))
            ),
            sections = emptyList(),
            participants = listOf(UserShortView(id = 1, fullName = "Alex"))
        )

        val approach = getView<DraftApproachView>(makePath(1))

        assertEquals(expectedView, approach)
    }

    /**
     * Should return ApiExceptionView
     *
     * Expected 404 http code and 404_003 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `get not existent approach test`() {
        val request = getRequest(makePath(199))

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_003, exceptionView.code)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * Test check method creation
     */
    @Test
    fun `create method with base sections test`() {
        val loginAccessToken = loginAccessToken("email", "password")
        val dto = DraftApproachCreationDTO(
            name = "approach Z",
            initialCategoryId = 1
        )

        val created = postAuthorized<DraftApproachView>(path, dto, loginAccessToken)
        val approach = getView<DraftApproachView>(makePath(created.id))

        val expectedView = DraftApproachView(
            id = approach.id,
            name = "approach Z",
            categories = listOf(
                CategoryShortView(id = 1, name = "catalog 1", creationDate = timeOf(2020, 9, 17))
            ),
            sections = listOf(),
            participants = listOf(UserShortView(id = 1, fullName = "Alex"))
        )

        assertEquals(expectedView, approach)
    }

    /**
     * Test checks exception on attempt to create approach with wrong category id
     *
     * Expected 404 http code and 404_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `create method with wrong initial parent category id`() {
        val loginAccessToken = loginAccessToken("email", "password")
        val dto = DraftApproachCreationDTO(
            name = "approach Z",
            initialCategoryId = 199
        )
        val request = postRequestAuthorized(path, dto, loginAccessToken)

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_001, exceptionView.code)
        assertEquals(listOf(listOf("199")), exceptionView.arguments)
    }

    /**
     * Тест checks failure when creating section by regular user
     *
     * Expected 403 http code and 403_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `create method by regular user failure test`() {
        val loginAccessToken = loginAccessToken("email", "password")
        val dto = DraftApproachCreationDTO(
            name = "approach Z",
            initialCategoryId = 199
        )
        val request = postRequestAuthorized(path, dto, loginAccessToken)

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_001, exceptionView.code)
        assertEquals(listOf(listOf("199")), exceptionView.arguments)
    }


    private fun makePath(addition: Any): String {
        return "$path/$addition"
    }

    fun timeOf(year: Int, month: Int, day: Int): LocalDateTime {
        return LocalDateTime.of(year, month, day, 0, 0, 0)
    }

}