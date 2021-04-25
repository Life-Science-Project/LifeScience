package com.jetbrains.life_science.category.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.category.dto.CategoryDTO
import com.jetbrains.life_science.category.entity.Category
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
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

const val API_URL = "/api/categories"

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/scripts/add_test_data.sql")
@WithUserDetails("admin")
internal class CategoryControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    val jsonMapper = jacksonObjectMapper()

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    @Test
    @Transactional
    internal fun `add category`() {
        val categoryDto = CategoryDTO("sample category", 1, 0)
        val savedCategory = postToCategoryController(categoryDto)
        assertNotNull(savedCategory.parent)
    }

    @Test
    @Transactional
    internal fun `add category with no parent`() {
        val categoryDto = CategoryDTO("sample category", null, 0)
        val savedCategory = postToCategoryController(categoryDto)
        assertNull(savedCategory.parent)
    }

    @Test
    @Transactional
    internal fun `add category with non-existent parent`() {
        val categoryDto = CategoryDTO("null parent category", 100, 0)
        assertCategoryNotFound(categoryPostRequest(categoryDto))
    }

    @Test
    @Transactional
    internal fun `get category`() {
        mockMvc.get("$API_URL/{id}", 1)
            .andExpect { status().isOk }
            .andExpect { content().contentType("application/json") }
            .andExpect { jsonPath("$.id").value("1") }
    }

    @Test
    @Transactional
    internal fun `get non-existent category`() {
        assertCategoryNotFound(mockMvc.get("$API_URL/{id}", 100))
    }

    private fun postToCategoryController(categoryDto: CategoryDTO): Category {
        val mockMvcResult = categoryPostRequest(categoryDto)
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.id").exists()
            }
            .andReturn()

        val responseCategory = jsonMapper.readValue(mockMvcResult.response.contentAsString, CategoryView::class.java)
        return categoryRepository.findById(responseCategory.id).orElseThrow().also {
            assertEquals(categoryDto.name, it.name)
        }
    }

    private fun categoryPostRequest(categoryDto: CategoryDTO): ResultActionsDsl {
        return mockMvc.post(API_URL) {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper.writeValueAsString(categoryDto)
            accept = MediaType.APPLICATION_JSON
        }
    }

    private fun assertCategoryNotFound(result: ResultActionsDsl) {
        result.andExpect {
            status { isNotFound() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.message").value("Category not found")
        }
    }
}
