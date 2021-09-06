package com.jetbrains.life_science.container.protocol.draft.service

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.exception.not_found.ProtocolNotFoundException
import com.jetbrains.life_science.exception.request.RemoveOwnerFromParticipantsException
import com.jetbrains.life_science.container.protocol.draft.service.maker.makeDraftProtocolInfo
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.service.DraftProtocolService
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import com.jetbrains.life_science.util.UTCZone
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/protocol/draft_protocol_data.sql")
@Transactional
class DraftProtocolServiceTest {

    @Autowired
    lateinit var service: DraftProtocolService

    @Autowired
    lateinit var credentialsService: CredentialsService

    @Autowired
    lateinit var sectionService: SectionService

    /**
     * Should get existing draft protocol
     */
    @Test
    fun `get existing protocol`() {
        // Prepare data
        val id = 1L
        val expectedName = "draft_protocol_test"
        val expectedOwnerId = 1L

        // Action
        val draftProtocol = service.get(id)

        // Assert
        assertEquals(id, draftProtocol.id)
        assertEquals(expectedName, draftProtocol.name)
        assertEquals(expectedOwnerId, draftProtocol.owner.id)
        assertContainsParticipant(draftProtocol, expectedOwnerId)
        assertContainsParticipant(draftProtocol, 2L)
    }

    /**
     * Should throw DraftProtocolNotFound exception
     */
    @Test
    fun `get non-existing protocol`() {
        // Prepare data
        val draftProtocolId = 239L

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.get(draftProtocolId)
        }
    }

    /**
     * Should create new protocol
     */
    @Test
    fun `create new draft protocol`() {
        // Prepare data
        val owner = credentialsService.getById(1L)
        val approach = createPublicApproach(1L, "public_approach", owner)
        val info = makeDraftProtocolInfo(
            id = 4L,
            name = "test",
            owner = owner,
            approach = approach
        )

        // Action
        service.create(info)
        val draftProtocol = service.get(info.id)

        // Assert
        assertEquals(info.id, draftProtocol.id)
        assertEquals(info.name, draftProtocol.name)
        assertEquals(owner.id, draftProtocol.owner.id)
        assertEquals(approach.id, draftProtocol.approach.id)
        assertContainsParticipant(draftProtocol, owner.id)
    }

    /**
     * Should update existing protocol
     */
    @Test
    fun `update existing draft protocol`() {
        // Prepare
        val owner = credentialsService.getById(1L)
        val approach = createPublicApproach(2L, "second_public_approach", owner)
        val info = makeDraftProtocolInfo(
            id = 1L,
            name = "new_name",
            owner = owner,
            approach = approach
        )

        // Action
        service.update(info)
        val draftProtocol = service.get(info.id)

        // Assert
        assertEquals(info.id, draftProtocol.id)
        assertEquals(info.name, draftProtocol.name)
        assertEquals(owner.id, draftProtocol.owner.id)
        assertEquals(approach.id, draftProtocol.approach.id)
        assertContainsParticipant(draftProtocol, owner.id)
    }

    /**
     * Should throw DraftProtocolNotFound exception
     */
    @Test
    fun `update non-existing protocol`() {
        // Prepare data
        val owner = credentialsService.getById(1L)
        val approach = createPublicApproach(1L, "public_approach", owner)
        val info = makeDraftProtocolInfo(
            id = 239L,
            name = "update_protocol",
            owner = owner,
            approach = approach
        )

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.update(info)
        }
    }

    /**
     * Should delete existing draft protocol
     */
    @Test
    fun `delete existing draft protocol`() {
        // Prepare data
        val draftProtocolId = 1L

        // Action
        service.delete(draftProtocolId)

        // Assert
        assertThrows<ProtocolNotFoundException> {
            service.get(draftProtocolId)
        }
    }

    /**
     * Should throw DraftProtocolNotFound exception
     */
    @Test
    fun `delete not existing draft protocol`() {
        // Prepare data
        val draftProtocolId = 239L

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.delete(draftProtocolId)
        }
    }

    /**
     * Should add user to draft protocol participants
     */
    @Test
    fun `add user to participants`() {
        // Prepare data
        val draftProtocolId = 1L
        val user = credentialsService.getById(2L)

        // Action
        service.addParticipant(draftProtocolId, user)
        val draftProtocol = service.get(draftProtocolId)

        // Assert
        assertContainsParticipant(draftProtocol, user.id)
    }

    /**
     * Should throw DraftProtocolNotFoundException
     */
    @Test
    fun `add user to participants of not existing draft protocol`() {
        // Prepare data
        val draftProtocolId = 239L
        val user = credentialsService.getById(3L)

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.addParticipant(draftProtocolId, user)
        }
    }

    /**
     * Should remove user from draft protocol participants
     */
    @Test
    fun `remove user from participants`() {
        // Prepare data
        val draftProtocolId = 1L
        val user = credentialsService.getById(2L)

        // Action
        service.removeParticipant(draftProtocolId, user)
        val draftProtocol = service.get(draftProtocolId)

        // Assert
        assertNotContainsParticipant(draftProtocol, user.id)
    }

    /**
     * Should throw RemoveOwnerFromParticipantsException
     */
    @Test
    fun `remove owner from participants`() {
        // Prepare data
        val draftProtocolId = 1L
        val user = credentialsService.getById(1L)

        // Action & Assert
        assertThrows<RemoveOwnerFromParticipantsException> {
            service.removeParticipant(draftProtocolId, user)
        }
    }

    /**
     * Should throw DraftProtocolNotFoundException
     */
    @Test
    fun `remove user from participants of non-existing draft protocol`() {
        // Prepare data
        val draftProtocolId = 239L
        val user = credentialsService.getById(1L)

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.removeParticipant(draftProtocolId, user)
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
        val draftProtocol = service.get(protocolId)

        // Assert
        assertContainsSection(draftProtocol, section.id)
    }

    /**
     * Should throw DraftProtocolNotFoundException
     */
    @Test
    fun `add section to sections of not existing draft protocol`() {
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
        val protocolId = 2L
        val section = sectionService.getById(1L)

        // Action
        service.removeSection(protocolId, section)
        val draftProtocol = service.get(protocolId)

        // Assert
        assertNotContainsSection(draftProtocol, section.id)
    }

    /**
     * Should throw DraftApproachNotFoundException
     */
    @Test
    fun `remove section from sections of non-existing draft approach`() {
        // Prepare data
        val protocolId = 666L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<ProtocolNotFoundException> {
            service.removeSection(protocolId, section)
        }
    }

    private fun assertContainsParticipant(draftProtocol: DraftProtocol, userId: Long) {
        assertTrue(draftProtocol.participants.any { it.id == userId })
    }

    private fun assertNotContainsParticipant(draftProtocol: DraftProtocol, userId: Long) {
        assertFalse(draftProtocol.participants.any { it.id == userId })
    }

    private fun assertContainsSection(draftProtocol: DraftProtocol, sectionId: Long) {
        assertTrue(draftProtocol.sections.any { it.id == sectionId })
    }

    private fun assertNotContainsSection(draftProtocol: DraftProtocol, sectionId: Long) {
        assertFalse(draftProtocol.sections.any { it.id == sectionId })
    }

    private fun createPublicApproach(id: Long, name: String, owner: Credentials) =
        PublicApproach(
            id = id,
            name = name,
            aliases = mutableListOf(),
            owner = owner,
            tags = mutableListOf(),
            sections = mutableListOf(),
            coAuthors = mutableListOf(),
            categories = mutableListOf(),
            protocols = mutableListOf(),
            creationDate = LocalDateTime.now(UTCZone)
        )
}
