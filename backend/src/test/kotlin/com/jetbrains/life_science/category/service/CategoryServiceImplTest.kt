package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.jetbrains.life_science.exception.CategoryNotFoundException
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/add_test_data.sql")
internal class CategoryServiceImplTest {

    @MockBean
    lateinit var contentVersionRepository: ContentVersionRepository

    @Autowired
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Test
    @Transactional
    internal fun `add category without parent`() {
        val mock = mock<CategoryInfo> {
            on { name } doReturn "sample category"
            on { parentId } doReturn null
        }

        val category = categoryService.createCategory(mock)
        val savedcategory = categoryRepository.findById(category.id).orElseThrow()

        assertEquals("sample category", savedcategory.name)
        assertNull(savedcategory.parent)
    }

    @Test
    @Transactional
    internal fun `add category with parent`() {
        val mock = mock<CategoryInfo> {
            on { name } doReturn "sample category"
            on { parentId } doReturn 1L
        }

        val category = categoryService.createCategory(mock)
        val savedcategory = categoryRepository.findById(category.id).orElseThrow()

        assertEquals("sample category", savedcategory.name)
        assertNotNull(category.parent)
        assertEquals("root", category.parent?.name)
        assertEquals(1, category.parent?.id)
    }

    @Test
    @Transactional
    internal fun `delete a category`() {
        categoryService.deleteCategory(3)
        assertFalse(categoryRepository.existsById(3))
    }

    @Test
    @Transactional
    internal fun `delete a non-existent category`() {
        assertThrows(CategoryNotFoundException::class.java) { categoryService.deleteCategory(-1L) }
    }

    @Test
    @Transactional
    internal fun `get category test`() {
        val category = categoryService.getCategory(1L)
        assertEquals(1L, category.id)
        assertEquals("root", category.name)
    }

    @Test
    @Transactional
    internal fun `get a non-existent category`() {
        assertThrows(CategoryNotFoundException::class.java) { categoryService.getCategory(-1L) }
    }
}
