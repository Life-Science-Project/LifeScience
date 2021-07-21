package com.jetbrains.life_science.protocol.published.service

import com.jetbrains.life_science.exception.not_found.PublicProtocolNotFoundException
import com.jetbrains.life_science.protocol.entity.PublicProtocol
import com.jetbrains.life_science.protocol.published.service.maker.makePublicProtocolInfo
import com.jetbrains.life_science.protocol.service.DraftProtocolService
import com.jetbrains.life_science.protocol.service.PublicProtocolService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/protocol/public_protocol_data.sql")
@Transactional
class PublicProtocolServiceTest {

    @Autowired
    lateinit var service: PublicProtocolService

    @Autowired
    lateinit var draftProtocolService: DraftProtocolService

    /**
     * Should create new protocol
     */
    @Test
    fun `create new draft approach`() {
        // Prepare data
        val draftProtocol = draftProtocolService.get(2L)
        val info = makePublicProtocolInfo(
            draftProtocol
        )

        // Action
        val createdProtocol = service.create(info)
        val publicProtocol = service.get(createdProtocol.id)

        // Assert
        assertEquals(info.protocol.name, publicProtocol.name)
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
    fun `get non-existing approach`() {
        // Prepare data
        val protocolId = 666L

        // Action & Assert
        assertThrows<PublicProtocolNotFoundException> {
            service.get(protocolId)
        }
    }

    private fun assertContainsCoAuthor(publicProtocol: PublicProtocol, userId: Long) {
        Assertions.assertTrue(publicProtocol.coAuthors.any { it.id == userId })
    }
}