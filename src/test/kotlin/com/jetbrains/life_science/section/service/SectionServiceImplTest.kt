package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.exceptions.SectionNotFoundException // ktlint-disable
import com.jetbrains.life_science.section.entity.SectionInfo
import com.jetbrains.life_science.section.repository.SectionRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
internal class SectionServiceImplTest {

    @Autowired
    lateinit var sectionService: SectionService

    @Autowired
    lateinit var sectionRepository: SectionRepository

    @BeforeEach
    @Sql("/scripts/test_trunc_data.sql")
    internal fun setUp() {

    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `add section without parent`() {
        val mock = mock<SectionInfo> {
            on { getName() } doReturn "sample section"
            on { getParentID() } doReturn null
        }

        val section = sectionService.addSection(mock)
        val savedSection = sectionRepository.findById(section.id).orElseThrow()

        assertEquals("sample section", savedSection.name)
        assertNull(savedSection.parent)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `add section with parent`() {
        val mock = mock<SectionInfo> {
            on { getName() } doReturn "sample section"
            on { getParentID() } doReturn 1L
        }

        val section = sectionService.addSection(mock)
        val savedSection = sectionRepository.findById(section.id).orElseThrow()

        assertEquals("sample section", savedSection.name)
        assertNotNull(section.parent)
        assertEquals("root", section.parent?.name)
        assertEquals(1, section.parent?.id)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `delete a section`() {
        sectionService.deleteSection(3)
        assertFalse(sectionRepository.existsById(3))
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `delete a non-existent section`() {
        assertThrows(SectionNotFoundException::class.java) { sectionService.deleteSection(-1L) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `get section test`() {
        val section = sectionService.getSection(1L)
        assertEquals(1L, section.id)
        assertEquals("root", section.name)
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `get a non-existent section`() {
        assertThrows(SectionNotFoundException::class.java) { sectionService.getSection(-1L) }
    }

    @Test
    @Sql("/scripts/test_common_data.sql")
    @Transactional
    internal fun `get children of a section`() {
        val children = sectionService.getChildren(1)
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
        assertThrows(SectionNotFoundException::class.java) { sectionService.getChildren(-1L) }
    }
}
