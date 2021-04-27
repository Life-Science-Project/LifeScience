package com.jetbrains.life_science.category.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.article.master.view.ArticleView
import com.jetbrains.life_science.category.dto.CategoryDTO
import com.jetbrains.life_science.category.view.CategoryView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
@Transactional
internal class CategoryControllerTest :
    ControllerTest<CategoryDTO, CategoryView>("Category", CategoryView::class.java) {

    init {
        apiUrl = "/api/categories"
    }

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    /**
     * Should get all categories with no parent
     */
    @Test
    internal fun `get root categories`() {
        val categories = getRootCategories()
        assertTrue(categories.isNotEmpty())
        for (category in categories) {
            assertNull(category.parentId)
        }
    }

    /**
     * Should get expected category
     */
    @Test
    internal fun `get existing category`() {
        val category = get(2)
        val expectedCategory = CategoryView(
            2, 1, "child category 1", 2,
            listOf(), listOf(ArticleView(2, null))
        )
        assertEquals(expectedCategory, category)
    }

    /**
     * Should get status code 404
     */
    @Test
    internal fun `get non-existent category`() {
        assertNotFound(getRequest(100))
    }

    /**
     * Should create new category
     */
    @Test
    internal fun `create category`() {
        val categoryDto = CategoryDTO("sample category", 1, 0)
        createCategory(categoryDto)
    }

    /**
     * Should create new root category
     */
    @Test
    internal fun `create category with no parent`() {
        val rootCategories = getRootCategories().size
        val categoryDto = CategoryDTO("sample category", null, 0)
        createCategory(categoryDto)
        val categories = getRootCategories()
        assertEquals(rootCategories + 1, categories.size)
        for (category in categories) {
            assertNull(category.parentId)
        }
    }

    /**
     * Should get status code 404, because parent doesn't exist
     */
    @Test
    internal fun `create category with non-existent parent`() {
        val categoryDto = CategoryDTO("null parent category", 100, 0)
        assertNotFound(postRequest(categoryDto))
    }

    /**
     * Should update existing category
     */
    @Test
    internal fun `update existing category`() {
        val categoryDto = CategoryDTO("updated sample category", null, 150)
        updateCategory(1, categoryDto)
    }

    /**
     * Updated category with null parent should be added to root
     */
    @Test
    internal fun `update category to root`() {
        val rootCategories = getRootCategories().size
        val categoryDto = CategoryDTO("updated sample category", null, 150)
        updateCategory(2, categoryDto)
        val categories = getRootCategories()
        assertEquals(rootCategories + 1, categories.size)
        for (category in categories) {
            assertNull(category.parentId)
        }
    }

    /**
     * Should get 404, because category doesn't exist
     */
    @Test
    internal fun `update non-existent category`() {
        val categoryDto = CategoryDTO("no category", null, 150)
        assertNotFound(putRequest(100, categoryDto))
    }

    /**
     * Should update category parent and add subcategory to parent
     */
    @Test
    internal fun `update category parent`() {
        val oldParent = get(3)
        val categoryDto = CategoryDTO("updated sample category", oldParent.id, 150)
        updateCategory(2, categoryDto)
        val newParent = get(oldParent.id)
        assertEquals(oldParent.subcategories.size + 1, newParent.subcategories.size)
    }

    /**
     * Should get 404, because parent doesn't exist
     */
    @Test
    internal fun `update non-existent parent`() {
        val categoryDto = CategoryDTO("updated sample category", 8, 9)
        assertNotFound(putRequest(1, categoryDto))
    }

    @Test
    internal fun `delete existing category`() {
        delete(3)
        assertNotFound(getRequest(3))
    }

    /**
     * Should get 400, because deleting non-empty categories is prohibited
     */
    @Test
    @Transactional
    internal fun `delete category with children`() {
        assertNotEmpty(deleteRequest(1))
    }

    /**
     * Should get 404, because category doesn't exist
     */
    @Test
    @Transactional
    internal fun `delete non-existent category`() {
        assertNotFound(deleteRequest(100))
    }

    /**
     * Should forbid access to modifying methods
     */
    @Test
    @WithUserDetails("user")
    internal fun `user privileges`() {
        getRootCategories()
        getRequest(1)
        val categoryDto = CategoryDTO("sample category", 1, 0)
        assertForbidden(postRequest(categoryDto))
        assertForbidden(putRequest(1, categoryDto))
        assertForbidden(deleteRequest(3))
    }

    /**
     * Should forbid access to modifying methods
     */
    @Test
    @WithAnonymousUser
    internal fun `anonymous privileges`() {
        getRootCategories()
        getRequest(1)
        val categoryDto = CategoryDTO("sample category", 1, 0)
        assertUnauthenticated(postRequest(categoryDto))
        assertUnauthenticated(putRequest(1, categoryDto))
        assertUnauthenticated(deleteRequest(3))
    }

    private fun getRootCategories(): List<CategoryView> {
        val categories = mockMvc.get("$apiUrl/root")
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }.andReturn().response.contentAsString
        return getViewsFromJson(categories)
    }

    private fun createCategory(dto: CategoryDTO) {
        val responseCategory = post(dto)
        assertNotNull(responseCategory.id)
        val savedCategory = get(responseCategory.id)
        val expectedCategory = CategoryView(responseCategory.id, dto.parentId, dto.name, dto.order, listOf(), listOf())
        assertEquals(expectedCategory, savedCategory)
    }

    private fun updateCategory(id: Long, dto: CategoryDTO) {
        val oldCategory = get(id)
        val responseCategory = put(id, dto)
        assertEquals(id, responseCategory.id)
        val updatedCategory = get(responseCategory.id)
        val expectedCategory = CategoryView(
            id = oldCategory.id,
            parentId = dto.parentId,
            name = dto.name,
            order = dto.order,
            subcategories = oldCategory.subcategories,
            articles = oldCategory.articles
        )
        assertEquals(expectedCategory, updatedCategory)
    }

    protected fun assertNotEmpty(result: ResultActionsDsl) {
        result.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message") { value("Category not empty") }
        }
    }
}
