package com.jetbrains.life_science.section.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.junit.jupiter.api.Assertions.assertTrue

@SpringBootTest
@Transactional
internal class SectionEntityTests {

    @Test
    fun `compare equal sections`() {
        // Prepare data
        val section1 = Section(
            id = 239L,
            name = "section",
            hidden = true,
            published = true,
            order = 1
        )
        val section2 = Section(
            id = 239L,
            name = "section",
            hidden = true,
            published = true,
            order = 1
        )

        // Action & Assert
        assertTrue(section1 == section2)
        assertTrue(section2 == section1)
    }

    @Test
    fun `compare unequal sections`() {
        // Prepare data
        val section = Section(
            id = 239L,
            name = "section",
            hidden = true,
            published = true,
            order = 1
        )
        val tests = listOf(
            123,
            null,
            Section(id = 1L, name = "section", hidden = true, published = true, order = 1),
            Section(id = 239L, name = "other section", hidden = true, published = true, order = 1),
            Section(id = 239L, name = "section", hidden = false, published = true, order = 1),
            Section(id = 239L, name = "section", hidden = true, published = false, order = 1),
            Section(id = 239L, name = "section", hidden = true, published = true, order = 22)
        )

        // Action & Assert
        tests.forEach {
            assertTrue(section != it)
        }
    }

    @Test
    fun `evaluate hashCode`() {
        // Prepare data
        val section = Section(
            id = 239L,
            name = "section",
            hidden = true,
            published = true,
            order = 1
        )
        val expectedHashCode = 656043659

        // Action & Assert
        assertEquals(expectedHashCode, section.hashCode())
    }
}
