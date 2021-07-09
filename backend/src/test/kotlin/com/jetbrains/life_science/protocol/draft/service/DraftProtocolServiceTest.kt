package com.jetbrains.life_science.protocol.draft.service

import com.jetbrains.life_science.approach.entity.PublicApproach
import com.jetbrains.life_science.exception.not_found.DraftProtocolNotFoundException
import com.jetbrains.life_science.protocol.draft.service.marker.makeDraftProtocolInfo
import com.jetbrains.life_science.protocol.service.DraftProtocolService
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.junit.jupiter.api.Assertions.assertEquals
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
class DraftProtocolServiceTest {

    @Autowired
    lateinit var draftProtocolservice: DraftProtocolService

    @Autowired
    lateinit var credentialsService: CredentialsService

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
        val draftProtocol = draftProtocolservice.get(id)

        // Assert
        assertEquals(expectedName, draftProtocol.name)
        assertEquals(expectedOwnerId, draftProtocol.owner.id)
        assertTrue(draftProtocol.participants.any { it.id == expectedOwnerId })
        assertTrue(draftProtocol.participants.any { it.id == 2L })
    }

    /**
     * Should throw DraftProtocolNotFound exception
     */
    @Test
    fun `get non-existing protocol`() {
        // Prepare data
        val draftProtocolId = 239L

        // Action & Assert
        assertThrows<DraftProtocolNotFoundException> {
            draftProtocolservice.get(draftProtocolId)
        }
    }

    /**
     * Should create new protocol
     */
    @Test
    fun `create new draft protocol`() {
        // Prepare data
        val owner = credentialsService.getById(1L)
        val approach = PublicApproach(
            id = 1L,
            name = "public_approach",
            owner = owner,
            tags = listOf(),
            sections = mutableListOf(),
            coAuthors = mutableListOf(),
            categories = mutableListOf(),
            protocols = mutableListOf()
        )
        val info = makeDraftProtocolInfo(
            id = 0L,
            name = "test",
            owner = owner,
            approach = approach
        )

        // Action
        val draftProtocol = draftProtocolservice.create(info)

        // Assert
        assertEquals(info.name, draftProtocol.name)
        assertEquals(owner.id, draftProtocol.owner.id)
        assertEquals(approach.id, draftProtocol.approach.id)
        assertTrue(draftProtocol.participants.any { it.id == owner.id })
    }

    /**
     * Should throw PublicApproachNotFoundException
     */
    @Test
    // TODO::Not yet implemented
    fun `create new draft protocol to non-existing public approach`() {
    }

    /**
     * Should throw UserNotFoundException
     */
    // TODO::Not yet implemented
    @Test
    fun `create new draft protocol with non-existing owner`() {
    }

    /**
     * Should update existing protocol
     */
    @Test
    fun `update existing draft protocol`() {
        // Prepare
        val owner = credentialsService.getById(1L)
        val approach = PublicApproach(
            id = 2L,
            name = "second_public_approach",
            owner = owner,
            tags = listOf(),
            sections = mutableListOf(),
            coAuthors = mutableListOf(),
            categories = mutableListOf(),
            protocols = mutableListOf()
        )
        val info = makeDraftProtocolInfo(
            id = 1L,
            name = "new_name",
            owner = owner,
            approach = approach
        )

        // Action
        val draftProtocol = draftProtocolservice.update(info)

        // Assert
        assertEquals(info.id, draftProtocol.id)
        assertEquals(info.name, draftProtocol.name)
        assertEquals(owner.id, draftProtocol.owner.id)
        assertEquals(approach.id, draftProtocol.approach.id)
        assertTrue(draftProtocol.participants.any { it.id == owner.id })
    }

    /**
     * Should throw DraftProtocolNotFound exception
     */
    @Test
    fun `update non-existing protocol`() {
        // Prepare data
        val owner = credentialsService.getById(1L)
        val approach = PublicApproach(
            id = 1L,
            name = "public_approach",
            owner = owner,
            tags = listOf(),
            sections = mutableListOf(),
            coAuthors = mutableListOf(),
            categories = mutableListOf(),
            protocols = mutableListOf()
        )
        val info = makeDraftProtocolInfo(
            id = 239L,
            name = "update_protocol",
            owner = owner,
            approach = approach
        )

        // Action & Assert
        assertThrows<DraftProtocolNotFoundException> {
            draftProtocolservice.update(info)
        }
    }

    /**
     * Should throw PublicApproachNotFound exception
     * Should throw UserNotFound exception
     */
    // TODO::Not yet implemented
    @Test
    fun `update existing protocol with wring data`() {
    }

    /**
     * Should delete existing draft protocol
     */
    @Test
    fun `delete existing draft protocol`() {
        // Prepare data
        val draftProtocolId = 1L

        // Action
        draftProtocolservice.delete(draftProtocolId)

        // Assert
        assertThrows<DraftProtocolNotFoundException> {
            draftProtocolservice.get(draftProtocolId)
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
        assertThrows<DraftProtocolNotFoundException> {
            draftProtocolservice.delete(draftProtocolId)
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
        val draftProtocol = draftProtocolservice.addParticipant(draftProtocolId, user)

        // Assert
        assertTrue(draftProtocol.participants.any { it.id == user.id })
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
        assertThrows<DraftProtocolNotFoundException> {
            draftProtocolservice.addParticipant(draftProtocolId, user)
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
        val draftProtocol = draftProtocolservice.removeParticipant(draftProtocolId, user)

        // Assert
        assertTrue(draftProtocol.participants.all { it.id != user.id })
    }

    /**
     * Should throw exception
     */
    // TODO::Not yet implemented
    @Test
    fun `remove owner from participants`() {
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
        assertThrows<DraftProtocolNotFoundException> {
            draftProtocolservice.removeParticipant(draftProtocolId, user)
        }
    }
}
