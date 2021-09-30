package com.jetbrains.life_science.container.approach.published.service

import com.jetbrains.life_science.category.search.service.CategorySearchUnitService
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.exception.not_found.ApproachNotFoundException
import com.jetbrains.life_science.section.service.SectionService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql(
    value = [
        "/scripts/initial_data.sql",
        "/scripts/approach/public_approach_data.sql"
    ]
)
@Transactional
class PublicApproachServiceTest {

    @MockBean
    lateinit var categorySearchUnitService: CategorySearchUnitService

    @MockBean
    lateinit var approachSearchUnitService: ApproachSearchUnitService

    @Autowired
    lateinit var service: PublicApproachService

    @Autowired
    lateinit var draftApproachService: DraftApproachService

    @Autowired
    lateinit var sectionService: SectionService

    /**
     * Should create new approach
     */
    @Test
    fun `create new public approach`() {
        // Prepare data
        val draftApproach = draftApproachService.get(1L)

        // Action
        val createdApproach = service.create(draftApproach)
        val publicApproach = service.get(createdApproach.id)

        // Assert
        assertEquals(draftApproach.name, publicApproach.name)
        assertEquals(draftApproach.aliases.toSet(), publicApproach.aliases.toSet())
        assertEquals(draftApproach.tags.toSet(), publicApproach.tags.toSet())
        assertContainsCoAuthor(publicApproach, draftApproach.owner.id)
        draftApproach.participants.forEach {
            assertContainsCoAuthor(publicApproach, it.id)
        }
        draftApproach.categories.forEach {
            assertContainsCategory(publicApproach, it.id)
        }
    }

    /**
     * Should get existing approach
     */
    @Test
    fun `get existing approach`() {
        // Prepare data
        val approachId = 1L
        val expectedOwnerId = 1L
        val secondCoAuthorId = 2L
        val coAuthorsExpectedCount = 2
        val expectedName = "approach 1"
        val expectedAliases = setOf("test alias")

        // Action
        val publicApproach = service.get(approachId)

        // Assert
        assertEquals(expectedName, publicApproach.name)
        assertEquals(expectedOwnerId, publicApproach.owner.id)
        assertEquals(expectedAliases, publicApproach.aliases.toSet())
        assertEquals(coAuthorsExpectedCount, publicApproach.coAuthors.size)
        assertContainsCoAuthor(publicApproach, expectedOwnerId)
        assertContainsCoAuthor(publicApproach, secondCoAuthorId)
    }

    /**
     * Should throw PublicApproachNotFound exception
     */
    @Test
    fun `get non-existing approach`() {
        // Prepare data
        val approachId = 666L

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.get(approachId)
        }
    }

    /**
     * Should add section to approach sections
     */
    @Test
    fun `add section to sections`() {
        // Prepare data
        val approachId = 1L
        val section = sectionService.getById(1L)

        // Action
        service.addSection(approachId, section)
        val publicApproach = service.get(approachId)

        // Assert
        assertContainsSection(publicApproach, section.id)
    }

    /**
     * Should throw PublicApproachNotFoundException
     */
    @Test
    fun `add section to sections of not existing public approach`() {
        // Prepare data
        val approachId = 666L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.addSection(approachId, section)
        }
    }

    /**
     * Should remove section from sections
     */
    @Test
    fun `remove section from sections`() {
        // Prepare data
        val approachId = 2L
        val section = sectionService.getById(1L)

        // Action
        service.removeSection(approachId, section)
        val publicApproach = service.get(approachId)

        // Assert
        assertNotContainsSection(publicApproach, section.id)
    }

    /**
     * Should throw PublicApproachNotFoundException
     */
    @Test
    fun `remove section from sections of non-existing public approach`() {
        // Prepare data
        val approachId = 666L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.removeSection(approachId, section)
        }
    }

    private fun assertContainsCoAuthor(publicApproach: PublicApproach, userId: Long) {
        assertTrue(publicApproach.coAuthors.any { it.id == userId })
    }

    private fun assertContainsCategory(publicApproach: PublicApproach, categoryId: Long) {
        assertTrue(publicApproach.categories.any { it.id == categoryId })
    }

    private fun assertContainsSection(publicApproach: PublicApproach, sectionId: Long) {
        assertTrue(publicApproach.sections.any { it.id == sectionId })
    }

    private fun assertNotContainsSection(publicApproach: PublicApproach, sectionId: Long) {
        assertFalse(publicApproach.sections.any { it.id == sectionId })
    }
}
