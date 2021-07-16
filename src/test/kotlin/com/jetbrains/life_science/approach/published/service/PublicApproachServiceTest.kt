package com.jetbrains.life_science.approach.published.service

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.approach.published.service.maker.makePublicApproachInfo
import com.jetbrains.life_science.approach.service.DraftApproachService
import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.exception.not_found.PublicApproachNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/approach/public_approach_data.sql")
@Transactional
class PublicApproachServiceTest {

    @Autowired
    lateinit var service: PublicApproachService

    @Autowired
    lateinit var draftApproachService: DraftApproachService

    /**
     * Should create new approach
     */
    @Test
    fun `create new draft approach`() {
        // Prepare data
        val draftApproach = draftApproachService.get(1L)
        val info = makePublicApproachInfo(
            draftApproach
        )

        // Action
        val createdApproach = service.create(info)
        val publicApproach = service.get(createdApproach.id)

        // Assert
        assertEquals(info.approach.name, publicApproach.name)
        assertEquals(info.approach.tags, publicApproach.tags)
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

        // Action
        val publicApproach = service.get(approachId)

        // Assert
        assertEquals(expectedName, publicApproach.name)
        assertEquals(expectedOwnerId, publicApproach.owner.id)
        assertEquals(coAuthorsExpectedCount, publicApproach.coAuthors.size)
        assertContainsCoAuthor(publicApproach, expectedOwnerId)
        assertContainsCoAuthor(publicApproach, secondCoAuthorId)
    }

    /**
     * Should throw DraftApproachNotFound exception
     */
    @Test
    fun `get non-existing approach`() {
        // Prepare data
        val approachId = 666L

        // Action & Assert
        assertThrows<PublicApproachNotFoundException> {
            service.get(approachId)
        }
    }

    private fun assertContainsCoAuthor(publicApproach: PublicApproach, userId: Long) {
        Assertions.assertTrue(publicApproach.coAuthors.any { it.id == userId })
    }

    private fun assertContainsCategory(publicApproach: PublicApproach, categoryId: Long) {
        Assertions.assertTrue(publicApproach.categories.any { it.id == categoryId })
    }
}
