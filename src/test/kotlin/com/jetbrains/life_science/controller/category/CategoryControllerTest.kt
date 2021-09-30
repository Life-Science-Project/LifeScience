package com.jetbrains.life_science.controller.category

import com.jetbrains.life_science.ApiTest
import com.jetbrains.life_science.category.search.Path
import com.jetbrains.life_science.category.search.PathUnit
import com.jetbrains.life_science.controller.approach.view.ApproachShortView
import com.jetbrains.life_science.controller.category.dto.CategoryAliasDTO
import com.jetbrains.life_science.controller.category.dto.CategoryCreationDTO
import com.jetbrains.life_science.controller.category.dto.CategoryUpdateDTO
import com.jetbrains.life_science.controller.category.view.CategoryShortView
import com.jetbrains.life_science.controller.category.view.CategoryView
import com.jetbrains.life_science.util.populator.ElasticPopulator
import org.elasticsearch.client.RestHighLevelClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import javax.annotation.PostConstruct

@Sql("/scripts/initial_data.sql")
internal class CategoryControllerTest : ApiTest() {

    val pathPrefix = "/api/categories"

    lateinit var elasticPopulator: ElasticPopulator

    @Autowired
    lateinit var highLevelClient: RestHighLevelClient

    @PostConstruct
    fun setup() {
        elasticPopulator = ElasticPopulator(highLevelClient).apply {
            addPopulator("category", "elastic/category.json")
        }
    }

    @BeforeEach
    fun resetElastic() {
        elasticPopulator.prepareData()
    }

    /**
     * Get root categories test.
     *
     * All root categories expected.
     */
    @Test
    fun `root categories get test`() {
        val categories = getView<List<CategoryShortView>>(makePath("/root"))

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
            aliases = emptyList(),
            creationDate = timeOf(2020, 9, 17),
            approaches = listOf(
                ApproachShortView(1, "approach 1", timeOf(2020, 12, 17), emptyList())
            ),
            subCategories = listOf(
                CategoryShortView(3, "child 1-2", timeOf(2020, 11, 17))
            )
        )

        assertEquals(expectedView, category)
    }

    /**
     * Category creation test with admin
     */
    @Test
    fun `create category test`() {
        val loginTokens = login("admin@gmail.ru", "password")
        val aliases = listOf(
            CategoryAliasDTO("second name"),
            CategoryAliasDTO("third name")
        )
        val categoryDTO = CategoryCreationDTO(
            name = "my category",
            aliases = aliases,
            initialParentId = 3
        )
        val createdCategory = postAuthorized<CategoryShortView>(pathPrefix, categoryDTO, loginTokens.accessToken)

        flushChanges()
        val category = getView<CategoryView>(makePath("/${createdCategory.id}"))

        assertEquals("my category", category.name)
        assertEquals("my category", createdCategory.name)
        assertEquals(aliases.map { it.alias }, category.aliases)
        assertCategoryAvailableFromParent(3, createdCategory.id)
    }

    /**
     * Category update test with admin
     */
    @Test
    fun `update category test`() {
        val loginTokens = login("admin@gmail.ru", "password")
        val categoryDTO = CategoryUpdateDTO(
            name = "changed name",
            parentsToAdd = listOf(3), parentsToDelete = listOf(2),
            aliases = listOf(CategoryAliasDTO("le name"))
        )

        val updatedCategory = patchAuthorized<CategoryView>(makePath("/5"), categoryDTO, loginTokens.accessToken)
        val category = getView<CategoryView>(makePath("/5"))

        assertCategoryNotAvailableFromParent(2, 5)
        assertCategoryAvailableFromParent(3, 5)
        assertEquals("changed name", updatedCategory.name)
        assertEquals("changed name", category.name)
        assertEquals("le name", category.aliases[0])
    }

    /**
     * Delete category test with admin
     */
    @Test
    fun `delete category test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")

        deleteAuthorized(makePath("/5"), accessToken)

        assertStatusAndReturn(HttpStatus.NOT_FOUND.value(), getRequest(makePath("/5")))
        assertCategoryNotAvailableFromParent(2, 5)
        assertCategoryNotAvailableFromParent(4, 5)
    }

    /**
     * Test checks deleting not empty category
     *
     * Expected 400 http code and 400_999 system code result
     * with message in view arguments.
     */
    @Test
    fun `not empty category delete test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")
        val request = deleteRequestAuthorized(makePath("/1"), accessToken)

        val exceptionView = getApiExceptionView(400, request)

        assertEquals(400_999, exceptionView.systemCode)
        assertEquals(
            listOf(listOf("Category with id \"1\" is not empty and can not be deleted")),
            exceptionView.arguments
        )
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

        assertEquals(404_001, exceptionView.systemCode)
        assertEquals(listOf(listOf("999")), exceptionView.arguments)
    }

    /**
     * Category to delete not found test.
     *
     * Expected 404 http code and 404_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `category to delete not found test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")

        val request = deleteRequestAuthorized(makePath("/999"), accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_001, exceptionView.systemCode)
        assertEquals(listOf(listOf("999")), exceptionView.arguments)
    }

    /**
     * Test checks api exception on post with wrong parent category id.
     *
     * Expected 404 http code and 404_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `category parent not found test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")
        val dto = CategoryCreationDTO("error", listOf(), 999)

        val request = postRequestAuthorized(pathPrefix, dto, accessToken)

        val exceptionView = getApiExceptionView(404, request)
        assertEquals(404_002, exceptionView.systemCode)
        assertEquals(listOf(listOf("999")), exceptionView.arguments)
    }

    /**
     * The test checks for an exception in case of an attempt to remove all parents from a category.
     *
     * Expected 400 http code and 400_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `all parents deleted test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")

        val categoryUpdateDTO = CategoryUpdateDTO(
            name = "changed name",
            aliases = emptyList(),
            parentsToAdd = listOf(), parentsToDelete = listOf(2, 4)
        )

        val request = patchRequestAuthorized(makePath("/5"), categoryUpdateDTO, accessToken)
        val exceptionView = getApiExceptionView(400, request)

        assertEquals(400_002, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * The test checks for an exception in case of an attempt to add non-existent parent.
     *
     * Expected 404 http code and 404_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `parent to add not found test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")

        val categoryUpdateDTO = CategoryUpdateDTO(
            name = "changed name",
            aliases = emptyList(),
            parentsToAdd = listOf(999), parentsToDelete = listOf(2)
        )

        val request = patchRequestAuthorized(makePath("/5"), categoryUpdateDTO, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_002, exceptionView.systemCode)
        assertEquals(listOf(listOf("999")), exceptionView.arguments)
    }

    /**
     * The test checks for an exception in case of an attempt to delete non-existent parent.
     *
     * Expected 404 http code and 404_002 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `parent to delete not found test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")

        val categoryUpdateDTO = CategoryUpdateDTO(
            name = "changed name",
            aliases = emptyList(),
            parentsToAdd = listOf(3), parentsToDelete = listOf(2, 999)
        )

        val request = patchRequestAuthorized(makePath("/5"), categoryUpdateDTO, accessToken)
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_002, exceptionView.systemCode)
        assertEquals(listOf(listOf("999")), exceptionView.arguments)
    }

    /**
     * The test checks for an exception in case of an attempt to create section with incorrect dto.
     *
     * Expected 400 http code and 400_003 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `incorrect dto test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")
        val dto = mapOf(
            "name" to "name1",
            "initialParentId" to "stringValue"
        )
        val request = postRequestAuthorized(pathPrefix, dto, accessToken)

        val exceptionView = getApiExceptionView(400, request)
        assertEquals(400_003, exceptionView.systemCode)
        assertEquals(listOf(listOf("initialParentId"), listOf("stringValue")), exceptionView.arguments)
    }

    /**
     * The test checks for an exception in the case of an DTO transmission with a missing required field
     *
     * Expected 400 http code and 400_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `broken dto test`() {
        val accessToken = loginAccessToken("admin@gmail.ru", "password")
        val dto = mapOf(
            "initialParentId" to 1
        )
        val request = postRequestAuthorized(pathPrefix, dto, accessToken)

        val exceptionView = getApiExceptionView(400, request)
        assertEquals(400_001, exceptionView.systemCode)
        assertEquals(listOf(listOf("name")), exceptionView.arguments)
    }

    /**
     * The test checks for an exception in the case of anonymous user creating category.
     *
     * Expected 403 http code and 403_000 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `anonymous user category creation test`() {
        val dto = CategoryCreationDTO("error", listOf(), 3)

        val request = postRequest(pathPrefix, dto)

        val exceptionView = getApiExceptionView(403, request)
        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * The test checks for an exception in the case of anonymous user updating category.
     *
     * Expected 403 http code and 403_000 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `anonymous user category update test`() {
        val categoryDTO = CategoryUpdateDTO(
            name = "changed name",
            aliases = emptyList(),
            parentsToAdd = listOf(3), parentsToDelete = listOf(2)
        )

        val request = patchRequest(pathPrefix, categoryDTO)

        val exceptionView = getApiExceptionView(403, request)
        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * The test checks for an exception in the case of anonymous user deleting category.
     *
     * Expected 403 http code and 403_000 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `anonymous user category delete test`() {
        val request = deleteRequest(makePath("/3"))

        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * The test checks for an exception in the case of regular user creating category.
     *
     * Expected 400 http code and 400_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `regular user category creation test`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        val dto = CategoryCreationDTO("error", listOf(), 3)

        val request = postRequestAuthorized(pathPrefix, dto, accessToken)

        val exceptionView = getApiExceptionView(403, request)
        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * The test checks for an exception in the case of regular user updating category.
     *
     * Expected 400 http code and 400_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `regular user category update test`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        val categoryDTO = CategoryUpdateDTO(
            name = "changed name",
            aliases = emptyList(),
            parentsToAdd = listOf(3), parentsToDelete = listOf(2)
        )

        val request = patchRequestAuthorized(makePath("/3"), categoryDTO, accessToken)

        val exceptionView = getApiExceptionView(403, request)
        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * The test checks for an exception in the case of regular user deleting category.
     *
     * Expected 400 http code and 400_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `regular user category delete test`() {
        val accessToken = loginAccessToken("simple@gmail.ru", "user123")
        val request = deleteRequestAuthorized(makePath("/3"), accessToken)

        val exceptionView = getApiExceptionView(403, request)

        assertEquals(403_000, exceptionView.systemCode)
        assertTrue(exceptionView.arguments.isEmpty())
    }

    /**
     * Should return list of path of existing category
     */
    @Test
    fun `get existing category paths`() {
        val expected: List<Path> = listOf(
            listOf(
                PathUnit(1, "root")
            )
        )

        val paths = getView<List<Path>>(makePath("/3/paths"))

        assertEquals(expected, paths)
    }

    /**
     * Expected 400 http code and 400_001 system code result
     * with requested category id in view arguments.
     */
    @Test
    fun `get non-existing category paths`() {
        val request = getRequest(makePath("/666/paths"))
        val exceptionView = getApiExceptionView(404, request)

        assertEquals(404_001, exceptionView.systemCode)
        assertEquals("666", exceptionView.arguments[0][0])
    }

    private fun assertCategoryAvailableFromParent(parentId: Long, childId: Long) {
        val parentCategory = getView<CategoryView>(makePath("/$parentId"))
        assertTrue(parentCategory.subCategories.any { it.id == childId })
    }

    private fun assertCategoryNotAvailableFromParent(parentId: Long, childId: Long) {
        val parentCategory = getView<CategoryView>(makePath("/$parentId"))
        assertTrue(parentCategory.subCategories.none { it.id == childId })
    }

    fun makePath(suffix: String): String {
        return pathPrefix + suffix
    }
}
