package com.jetbrains.life_science.controller.approach.draft

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.approach.draft.view.DraftApproachView
import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.user.UserShortView
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
            name = "approach 1",
            categories = listOf(
                CategoryShortView(1, "catalog 1", timeOf(2020, 9, 17)),
                CategoryShortView(2, "catalog 2", timeOf(2020, 10, 17))
            ),
            sections = emptyList(),
            participants = listOf(UserShortView(id = 1, fullName = "Alex"))
        )

        val approach = getView<DraftApproachView>(makePath(1))
    }

    private fun makePath(addition: Any): String {
        return "$path/$addition"
    }

    fun timeOf(year: Int, month: Int, day: Int): LocalDateTime {
        return LocalDateTime.of(year, month, day, 0, 0, 0)
    }

}