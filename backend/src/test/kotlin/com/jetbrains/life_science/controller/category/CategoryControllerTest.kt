package com.jetbrains.life_science.controller.category

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.category.dto.CategoryCreationDTO
import com.jetbrains.life_science.controller.category.dto.CategoryUpdateDTO
import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.category.view.CategoryView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDateTime

@Sql("/scripts/initial_data.sql")
internal class CategoryControllerTest : ApiTest() {


    val pathPrefix = "/api/categories"

    /**
     * Get root categories test.
     *
     * All root categories expected.
     */
    @Test
    fun `root categories get test`() {
        val categories = getView<List<CategoryShortView>>(pathPrefix)

        val expectedViews = listOf(
            CategoryShortView(1, "catalog 1", timeOf(2020, 9, 17)),
            CategoryShortView(2, "catalog 2", timeOf(2020, 10, 17)),
        )

        assertEquals(expectedViews, categories)
    }

    /**
     * Get category view test.
     */
    @Test
    fun `category get test`() {
        val category = getView<CategoryView>(makePath("/1"))

        val expectedView = CategoryView(
            name = "catalog 1",
            creationDate = timeOf(2020, 11, 17),
            approaches = listOf(
                ApproachShortView(1, "approach 1", timeOf(2020, 12, 17), emptyList())
            ),
            subCategories = listOf(
                CategoryShortView(3, "child 1-2", timeOf(2020, 12, 17))
            )
        )

        assertEquals(expectedView, category)
    }

    /**
     * Category creation test with admin
     */
    @Test
    fun `create category test`() {
        val loginTokens = login("email", "password")

        val categoryDTO = CategoryCreationDTO("my category", 3)
        val createdCategory = postAuthorized<CategoryShortView>(pathPrefix, categoryDTO, loginTokens.accessToken)
        val category = getView<CategoryView>(makePath("/${createdCategory.id}"))

        assertEquals("my category", category.name)
        assertEquals("my category", createdCategory.name)
        assertCategoryAvailableFromParent(3, createdCategory.id)
    }

    /**
     * Category update test with admin
     */
    @Test
    fun `update category test`() {
        val loginTokens = login("email", "password")
        val categoryDTO = CategoryUpdateDTO(
            name = "changed name",
            parentsToAdd = listOf(3), parentsToDelete = listOf(2)
        )

        val updatedCategory = patchAuthorized<CategoryView>(makePath("/5"), categoryDTO, loginTokens.accessToken)
        val category = getView<CategoryView>(pathPrefix)

        assertCategoryAvailableFromParent(2, 5)
        assertCategoryAvailableFromParent(3, 5)
        assertEquals("changed name", updatedCategory.name)
        assertEquals("changed name", category.name)
    }

    /**
     * Delete category test with admin
     */
    @Test
    fun `delete category test`() {
        val accessToken = loginAccessToken("email", "password")

        deleteAuthorized(makePath("/5"), accessToken)

        assertStatusAndReturn(HttpStatus.NOT_FOUND.value(), getRequest(makePath("/5")))
        assertCategoryNotAvailableFromParent(2, 5)
        assertCategoryNotAvailableFromParent(4, 5)
    }

    /**
     * Category not found test.
     *
     * Expected 404 http code and 404_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `category not found test`() {
        val request = getRequest(makePath("/999"))

        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_001, exceptionView)
        assertEquals(listOf(listOf(999)), exceptionView.arguments)
    }

    /**
     * Test checks api exception on post with wrong parent category id.
     *
     * Expected 404 http code and 404_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `category parent not found test`() {
        val accessToken = loginAccessToken("email", "password")
        val dto = CategoryCreationDTO("error", 999)

        val request = postRequestAuthorized(pathPrefix, dto, accessToken)

        val exceptionView = getApiExceptionView(404, request)
        assertEquals(404_002, exceptionView.code)
        assertEquals(listOf(listOf(999)), exceptionView.arguments)
    }

    /**
     * The test checks for an exception in case of an attempt to remove all parents from a category.
     *
     * Expected 400 http code and 400_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `all parents deleted test`() {
        val accessToken = loginAccessToken("email", "password")

        val categoryUpdateDTO = CategoryUpdateDTO(
            name = "changed name",
            parentsToAdd = listOf(), parentsToDelete = listOf(2, 4)
        )

        val request = patchRequestAuthorized(makePath("/5"), categoryUpdateDTO, accessToken)
        val exceptionView = getApiExceptionView(400, request)

        assertEquals(400_002, exceptionView.code)
    }

    /**
     * The test checks for an exception in case of an attempt to add non-existent parent.
     *
     * Expected 404 http code and 404_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `parent to add not found test`() {
        val accessToken = loginAccessToken("email", "password")

        val categoryUpdateDTO = CategoryUpdateDTO(
            name = "changed name",
            parentsToAdd = listOf(999), parentsToDelete = listOf(2)
        )

        val request = patchRequestAuthorized(makePath("/5"), categoryUpdateDTO, accessToken)
        val exceptionView = getApiExceptionView(400, request)

        assertEquals(400_002, exceptionView.code)
    }

    /**
     * The test checks for an exception in case of an attempt to delete non-existent parent.
     *
     * Expected 404 http code and 404_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `parent to delete not found test`() {
        val accessToken = loginAccessToken("email", "password")

        val categoryUpdateDTO = CategoryUpdateDTO(
            name = "changed name",
            parentsToAdd = listOf(3), parentsToDelete = listOf(2, 999)
        )

        val request = patchRequestAuthorized(makePath("/5"), categoryUpdateDTO, accessToken)
        val exceptionView = getApiExceptionView(400, request)

        assertEquals(400_002, exceptionView.code)
    }

    private fun assertCategoryAvailableFromParent(parentId: Long, childId: Long) {
        val parentCategory = getView<CategoryView>(makePath("/$parentId"))
        assertTrue(parentCategory.subCategories.any { it.id == childId })
    }

    private fun assertCategoryNotAvailableFromParent(parentId: Long, childId: Long) {
        val parentCategory = getView<CategoryView>(makePath("/$parentId"))
        assertTrue(parentCategory.subCategories.none { it.id == childId })
    }

    fun timeOf(year: Int, month: Int, day: Int): LocalDateTime {
        return LocalDateTime.of(year, month, day, 0, 0, 0)
    }

    fun makePath(suffix: String): String {
        return pathPrefix + suffix
    }


}