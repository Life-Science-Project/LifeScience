package com.jetbrains.life_science.category.service

import com.jetbrains.life_science.exception.SectionNotFoundException
import com.jetbrains.life_science.category.entity.CategoryInfo
import com.jetbrains.life_science.category.repository.CategoryRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class CategoryServiceImplTest {

    @Autowired
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @BeforeEach
    @Sql("/scripts/test_trunc_data.sql")
    internal fun setUp() {
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `add section without parent`() {
        val mock = mock<CategoryInfo> {
            on { getName() } doReturn "sample section"
            on { getParentID() } doReturn null
        }

        val section = categoryService.createCategory(mock)
        val savedSection = categoryRepository.findById(section.id).orElseThrow()

        assertEquals("sample section", savedSection.name)
        assertNull(savedSection.parent)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `add section with parent`() {
        val mock = mock<CategoryInfo> {
            on { getName() } doReturn "sample section"
            on { getParentID() } doReturn 1L
        }

        val section = categoryService.createCategory(mock)
        val savedSection = categoryRepository.findById(section.id).orElseThrow()

        assertEquals("sample section", savedSection.name)
        assertNotNull(section.parent)
        assertEquals("root", section.parent?.name)
        assertEquals(1, section.parent?.id)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `delete a section`() {
        categoryService.deleteCategory(3)
        assertFalse(categoryRepository.existsById(3))
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `delete a non-existent section`() {
        assertThrows(SectionNotFoundException::class.java) { categoryService.deleteCategory(-1L) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `get section test`() {
        val section = categoryService.getCategory(1L)
        assertEquals(1L, section.id)
        assertEquals("root", section.name)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `get a non-existent section`() {
        assertThrows(SectionNotFoundException::class.java) { categoryService.getCategory(-1L) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `get children of a section`() {
        val children = categoryService.getChildren(1)
        assertEquals(2, children.size)

        assertEquals(2, children[0].id)
        assertEquals("child section 1", children[0].name)

        assertEquals(3, children[1].id)
        assertEquals("child section 2", children[1].name)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `get children of a non-existent section`() {
        assertThrows(SectionNotFoundException::class.java) { categoryService.getChildren(-1L) }
    }
}
