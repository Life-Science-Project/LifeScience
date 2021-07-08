package com.jetbrains.life_science.approach.draft.service

import com.jetbrains.life_science.approach.draft.service.maker.makeDraftApproachInfo
import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.exception.not_found.DraftApproachNotFoundException
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
@Sql("/scripts/add_test_data.sql")
@Transactional
class DraftApproachServiceTest {

    @Autowired
    lateinit var service: DraftApproachService

    @Autowired
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var credentialsService: CredentialsService

    /**
     * Should create new approach
     */
    @Test
    fun `create new draft approach`() {
        // Prepare
        val category = categoryService.getCategory(0)
        val info = makeDraftApproachInfo(
            id = 0L,
            name = "bradford",
            tags = listOf(),
            categories = listOf(
                category
            )
        )

        // Action
        val draftApproach = service.create(info)

        // Assert
        assertEquals(info.name, draftApproach.name)
        assertEquals(info.tags, draftApproach.tags)
        assertTrue(draftApproach.categories.any { it.id == category.id })
    }

    /**
     * Should get existing approach
     */
    @Test
    fun `get existing approach`() {
        // Prepare
        val approachId = 1L
        val expectedName = "first approach"

        // Action
        val draftApproach = service.get(approachId)

        // Assert
        assertEquals(expectedName, draftApproach)
    }

    /**
     * Should throw DraftApproachNotFound exception
     */
    @Test
    fun `get not existing approach`() {
        // Prepare
        val approachId = 666L

        // Action & Assert
        assertThrows<DraftApproachNotFoundException> {
            service.get(approachId)
        }
    }

    /**
     * Should update existing approach
     */
    @Test
    fun `update existing approach`() {
        // Prepare
        val category = categoryService.getCategory(0)
        val info = makeDraftApproachInfo(
            id = 1L,
            name = "updated name",
            tags = listOf(),
            categories = listOf(
                category
            )
        )

        // Action
        val draftApproach = service.update(info)

        // Assert
        assertEquals(info.id, draftApproach.id)
        assertEquals(info.name, draftApproach.name)
        assertEquals(info.tags, draftApproach.tags)
        assertTrue(draftApproach.categories.any { it.id == category.id })
    }

    /**
     * Should throw DraftApproachNotFound exception
     */
    @Test
    fun `update not existing approach`() {
        // Prepare
        val category = categoryService.getCategory(0)
        val info = makeDraftApproachInfo(
            id = 666L,
            name = "updated name",
            tags = listOf(),
            categories = listOf(
                category
            )
        )

        // Action & Assert
        assertThrows<DraftApproachNotFoundException> {
            service.update(info)
        }
    }

    /**
     * Should delete existing approach
     */
    @Test
    fun `delete existing approach`() {
        // Prepare
        val draftApproachId = 1L

        // Action
        service.delete(draftApproachId)

        // Assert
        assertThrows<DraftApproachNotFoundException> {
            service.get(draftApproachId)
        }
    }

    /**
     * Should throw DraftApproachNotFound exception
     */
    @Test
    fun `delete not existing approach`() {
        // Prepare
        val draftApproachId = 1L

        // Action & Assert
        assertThrows<DraftApproachNotFoundException> {
            service.delete(draftApproachId)
        }
    }

    /**
     * Should add user to participants
     */
    @Test
    fun `add user to participants`() {
        // Prepare
        val approachId = 1L
        val user = credentialsService.getByEmail("email")

        // Action
        val draftApproach = service.addParticipant(approachId, user)

        // Assert
        assertTrue(draftApproach.participants.any { it.id == user.id })
    }

    /**
     * Should throw DraftApproachNotFoundException
     */
    @Test
    fun `add user to participants of not existing draft approach`() {
        // Prepare
        val approachId = 666L
        val user = credentialsService.getByEmail("email")

        // Action & Assert
        assertThrows<DraftApproachNotFoundException> {
            service.addParticipant(approachId, user)
        }
    }

    /**
     * Should remove user to participants
     */
    @Test
    fun `remove user from participants`() {
        // Prepare
        val approachId = 2L
        val user = credentialsService.getByEmail("email")

        // Action
        val draftApproach = service.removeParticipant(approachId, user)

        // Assert
        assertFalse(draftApproach.participants.any { it.id == user.id })
    }

    /**
     * Should throw DraftApproachNotFoundException
     */
    @Test
    fun `remove user to participants of not existing draft approach`() {
        // Prepare
        val approachId = 666L
        val user = credentialsService.getByEmail("email")

        // Action & Assert
        assertThrows<DraftApproachNotFoundException> {
            service.removeParticipant(approachId, user)
        }
    }
}
