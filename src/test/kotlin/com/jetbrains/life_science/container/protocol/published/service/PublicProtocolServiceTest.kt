package com.jetbrains.life_science.container.protocol.published.service

import com.jetbrains.life_science.exception.not_found.ProtocolNotFoundException
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.container.protocol.search.service.ProtocolSearchUnitService
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.section.service.SectionService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/protocol/public_protocol_data.sql")
@Transactional
class PublicProtocolServiceTest {

    @MockBean
    lateinit var protocolSearchUnitService: ProtocolSearchUnitService

    @Autowired
    lateinit var service: PublicProtocolService

    @Autowired
    lateinit var draftProtocolService: DraftProtocolService

    @Autowired
    lateinit var sectionService: SectionService

    /**
     * Should create new protocol
     */
    @Test
    fun `create new public protocol`() {
        // Prepare data
        val draftProtocol = draftProtocolService.get(2L)

        // Action
        val createdProtocol = service.create(draftProtocol)
        val publicProtocol = service.get(createdProtocol.id)

        // Assert
        assertEquals(draftProtocol.name, publicProtocol.name)
        assertContainsCoAuthor(publicProtocol, draftProtocol.owner.id)
        draftProtocol.participants.forEach {
            assertContainsCoAuthor(publicProtocol, it.id)
        }
    }

    /**
     * Should get existing protocol
     */
    @Test
    fun `get existing protocol`() {
        // Prepare data
        val protocolId = 1L
        val expectedOwnerId = 1L
        val secondCoAuthorId = 2L
        val coAuthorsExpectedCount = 2
        val expectedName = "first published"

        // Action
        val publicProtocol = service.get(protocolId)

        // Assert
        assertEquals(expectedName, publicProtocol.name)
        assertEquals(expectedOwnerId, publicProtocol.owner.id)
        assertEquals(coAuthorsExpectedCount, publicProtocol.coAuthors.size)
        assertContainsCoAuthor(publicProtocol, expectedOwnerId)
        assertContainsCoAuthor(publicProtocol, secondCoAuthorId)
    }

    /**
     * Should throw PublicProtocolNotFound exception
     */
    @Test
    fun `get non-existing protocol`() {
        // Prepare data
        val protocolId = 666L

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.get(protocolId)
        }
    }

    /**
     * Should add section to protocol sections
     */
    @Test
    fun `add section to sections`() {
        // Prepare data
        val protocolId = 1L
        val section = sectionService.getById(2L)

        // Action
        service.addSection(protocolId, section)
        val publicProtocol = service.get(protocolId)

        // Assert
        assertContainsSection(publicProtocol, section.id)
    }

    /**
     * Should do nothing
     */
    @Test
    fun `add section to sections when it already added`() {
        // Prepare data
        val protocolId = 1L
        val section = sectionService.getById(1L)

        // Action
        service.addSection(protocolId, section)
        val publicProtocol = service.get(protocolId)

        // Assert
        assertContainsSection(publicProtocol, section.id)
    }

    /**
     * Should throw PublicProtocolNotFoundException
     */
    @Test
    fun `add section to sections of not existing public protocol`() {
        // Prepare data
        val protocolId = 666L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.addSection(protocolId, section)
        }
    }

    /**
     * Should remove section from sections
     */
    @Test
    fun `remove section from sections`() {
        // Prepare data
        val protocolId = 1L
        val section = sectionService.getById(1L)

        // Action
        service.removeSection(protocolId, section)
        val publicProtocol = service.get(protocolId)

        // Assert
        assertNotContainsSection(publicProtocol, section.id)
    }

    /**
     * Should throw PublicProtocolNotFoundException
     */
    @Test
    fun `remove section from sections of non-existing public protocol`() {
        // Prepare data
        val protocolId = 666L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.removeSection(protocolId, section)
        }
    }

    /**
     * Should do nothing
     */
    @Test
    fun `remove section from sections when it already deleted`() {
        // Prepare data
        val protocolId = 1L
        val section = sectionService.getById(2L)

        // Action
        service.removeSection(protocolId, section)
        val publicProtocol = service.get(protocolId)

        // Assert
        assertNotContainsSection(publicProtocol, section.id)
    }

    private fun assertContainsCoAuthor(publicProtocol: PublicProtocol, userId: Long) {
        Assertions.assertTrue(publicProtocol.coAuthors.any { it.id == userId })
    }

    private fun assertContainsSection(publicProtocol: PublicProtocol, sectionId: Long) {
        Assertions.assertTrue(publicProtocol.sections.any { it.id == sectionId })
    }

    private fun assertNotContainsSection(publicProtocol: PublicProtocol, sectionId: Long) {
        Assertions.assertFalse(publicProtocol.sections.any { it.id == sectionId })
    }
}
