package com.jetbrains.life_science.container.approach.draft.service

import com.jetbrains.life_science.container.approach.draft.service.maker.makeDraftApproachInfo
import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.service.DraftApproachService
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.exception.not_found.ApproachNotFoundException
import com.jetbrains.life_science.exception.request.RemoveOwnerFromParticipantsException
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/section/section_data.sql", "/scripts/approach/draft_approach_data.sql")
@Transactional
class DraftApproachServiceTest {

    @Autowired
    lateinit var service: DraftApproachService

    @Autowired
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var credentialsService: CredentialsService

    @Autowired
    lateinit var sectionService: SectionService

    /**
     * Should create new approach
     */
    @Test
    fun `create new draft approach`() {
        // Prepare data
        val category = categoryService.getById(1)
        val owner = credentialsService.getById(1L)
        val info = makeDraftApproachInfo(
            id = 0L,
            name = "bradford",
            aliases = mutableListOf("bradford second name"),
            tags = listOf(),
            categories = listOf(
                category
            ),
            owner = owner
        )

        // Action
        val createdApproach = service.create(info)
        val draftApproach = service.get(createdApproach.id)

        // Assert
        assertEquals(info.name, draftApproach.name)
        assertEquals(info.tags, draftApproach.tags)
        assertEquals(info.aliases, draftApproach.aliases)
        assertContainsParticipant(draftApproach, owner.id)
        assertContainsCategory(draftApproach, category.id)
    }

    /**
     * Should get existing approach
     */
    @Test
    fun `get existing approach`() {
        // Prepare data
        val approachId = 1L
        val expectedOwnerId = 1L
        val secondParticipantId = 4L
        val expectedName = "approach 1"
        val expectedAliases = setOf("approach 1 alias", "approach 1 alias 2")

        // Action
        val draftApproach = service.get(approachId)

        // Assert
        assertEquals(expectedName, draftApproach.name)
        assertEquals(expectedOwnerId, draftApproach.owner.id)
        assertEquals(expectedAliases, draftApproach.aliases.toSet())
        assertContainsParticipant(draftApproach, expectedOwnerId)
        assertContainsParticipant(draftApproach, secondParticipantId)
    }

    /**
     * Should throw DraftApproachNotFound exception
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
     * Should update existing approach
     */
    @Test
    fun `update existing approach`() {
        // Prepare data
        val category = categoryService.getById(1)
        val owner = credentialsService.getById(1L)
        val info = makeDraftApproachInfo(
            id = 1L,
            name = "updated name",
            aliases = mutableListOf("new alias"),
            tags = listOf(),
            categories = listOf(
                category
            ),
            owner = owner
        )

        // Action
        service.update(info)
        val draftApproach = service.get(info.id)

        // Assert
        assertEquals(info.id, draftApproach.id)
        assertEquals(info.name, draftApproach.name)
        assertEquals(info.aliases, draftApproach.aliases)
        assertEquals(info.tags, draftApproach.tags)
        assertEquals(owner.id, draftApproach.owner.id)
        assertContainsCategory(draftApproach, category.id)
        assertContainsParticipant(draftApproach, owner.id)
    }

    /**
     * Should throw DraftApproachNotFound exception
     */
    @Test
    fun `update non-existing approach`() {
        // Prepare data
        val category = categoryService.getById(1)
        val owner = credentialsService.getById(1L)
        val info = makeDraftApproachInfo(
            id = 666L,
            name = "updated name",
            aliases = mutableListOf(),
            tags = listOf(),
            categories = listOf(
                category
            ),
            owner = owner
        )

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.update(info)
        }
    }

    /**
     * Should delete existing approach
     */
    @Test
    fun `delete existing approach`() {
        // Prepare data
        val draftApproachId = 1L

        // Action
        service.delete(draftApproachId)

        // Assert
        assertThrows<ApproachNotFoundException> {
            service.get(draftApproachId)
        }
    }

    /**
     * Should throw DraftApproachNotFound exception
     */
    @Test
    fun `delete not existing approach`() {
        // Prepare data
        val draftApproachId = 239L

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.delete(draftApproachId)
        }
    }

    /**
     * Should add user to participants
     */
    @Test
    fun `add user to participants`() {
        // Prepare data
        val approachId = 1L
        val user = credentialsService.getById(3L)

        // Action
        service.addParticipant(approachId, user)
        val draftApproach = service.get(approachId)

        // Assert
        assertContainsParticipant(draftApproach, user.id)
    }

    /**
     * Should throw DraftApproachNotFoundException
     */
    @Test
    fun `add user to participants of not existing draft approach`() {
        // Prepare data
        val approachId = 666L
        val user = credentialsService.getById(3L)

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.addParticipant(approachId, user)
        }
    }

    /**
     * Should remove user from participants
     */
    @Test
    fun `remove user from participants`() {
        // Prepare data
        val approachId = 2L
        val user = credentialsService.getById(2L)

        // Action
        service.removeParticipant(approachId, user)
        val draftApproach = service.get(approachId)

        // Assert
        assertNotContainsParticipant(draftApproach, user.id)
    }

    /**
     * Should throw RemoveOwnerFromParticipantsException
     */
    @Test
    fun `remove owner from participants`() {
        // Prepare data
        val draftApproachId = 1L
        val user = credentialsService.getById(1L)

        // Action & Assert
        assertThrows<RemoveOwnerFromParticipantsException> {
            service.removeParticipant(draftApproachId, user)
        }
    }

    /**
     * Should throw DraftApproachNotFoundException
     */
    @Test
    fun `remove user from participants of non-existing draft approach`() {
        // Prepare data
        val approachId = 666L
        val user = credentialsService.getById(1L)

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.removeParticipant(approachId, user)
        }
    }

    /**
     * Should add section to approach sections
     */
    @Test
    fun `add section to sections`() {
        // Prepare data
        val approachId = 1L
        val section = sectionService.getById(2L)

        // Action
        service.addSection(approachId, section)
        val draftApproach = service.get(approachId)

        // Assert
        assertContainsSection(draftApproach, section.id)
    }

    /**
     * Should throw DraftApproachNotFoundException
     */
    @Test
    fun `add section to sections of not existing draft approach`() {
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
        val approachId = 3L
        val section = sectionService.getById(1L)

        // Action
        service.removeSection(approachId, section)
        val draftApproach = service.get(approachId)

        // Assert
        assertNotContainsSection(draftApproach, section.id)
    }

    /**
     * Should throw DraftApproachNotFoundException
     */
    @Test
    fun `remove section from sections of non-existing draft approach`() {
        // Prepare data
        val approachId = 666L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<ApproachNotFoundException> {
            service.removeSection(approachId, section)
        }
    }

    private fun assertContainsSection(draftApproach: DraftApproach, sectionId: Long) {
        assertTrue(draftApproach.sections.any { it.id == sectionId })
    }

    private fun assertNotContainsSection(draftApproach: DraftApproach, sectionId: Long) {
        assertFalse(draftApproach.sections.any { it.id == sectionId })
    }

    private fun assertContainsParticipant(draftApproach: DraftApproach, userId: Long) {
        assertTrue(draftApproach.participants.any { it.id == userId })
    }

    private fun assertNotContainsParticipant(draftApproach: DraftApproach, userId: Long) {
        assertFalse(draftApproach.participants.any { it.id == userId })
    }

    private fun assertContainsCategory(draftApproach: DraftApproach, categoryId: Long) {
        assertTrue(draftApproach.categories.any { it.id == categoryId })
    }
}
