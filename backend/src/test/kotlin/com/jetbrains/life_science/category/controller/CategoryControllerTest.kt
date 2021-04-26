package com.jetbrains.life_science.category.controller

import com.jetbrains.life_science.ControllerTest
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.category.dto.CategoryDTO
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.category.view.CategoryView
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.get
import org.springframework.transaction.annotation.Transactional

const val API_URL = "/api/categories"

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
internal class CategoryControllerTest :
    ControllerTest<CategoryDTO, CategoryView>("Category", CategoryView::class.java) {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    @Test
    @Transactional
    internal fun `add category`() {
        val categoryDto = CategoryDTO("sample category", 1, 0)
        addCategory(categoryDto)
    }

    @Test
    @Transactional
    internal fun `add category with no parent`() {
        val categoryDto = CategoryDTO("sample category", null, 0)
        addCategory(categoryDto)
    }

    private fun addCategory(dto: CategoryDTO) {
        val responseCategory = postToController(dto)
        assertNotNull(responseCategory.id)
        val savedCategory = getCategoryById(responseCategory.id)
        assertEquals(responseCategory.id, savedCategory.id)
        assertEquals(dto.parentId, savedCategory.parentId)
        assertEquals(dto.name, savedCategory.name)
    }

    @Test
    @Transactional
    internal fun `add category with non-existent parent`() {
        val categoryDto = CategoryDTO("null parent category", 100, 0)
        assertNotFound(postRequest(categoryDto))
    }

    @Test
    @Transactional
    internal fun `get category`() {
        val category = getCategoryById(1)
        assertEquals(1, category.id)
    }

    @Test
    @Transactional
    internal fun `get non-existent category`() {
        assertNotFound(mockMvc.get("$API_URL/{id}", 100))
    }

    private fun getCategoryById(id: Long): CategoryView {
        val category = mockMvc.get("$API_URL/{id}", id)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }.andReturn().response.contentAsString
        return getViewFromJson(category)
    }
}
