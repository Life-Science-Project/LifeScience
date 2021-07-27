package com.jetbrains.life_science.edit_record.protocol.service

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.edit_record.service.ProtocolEditRecordService
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.exception.section.SectionAlreadyDeletedException
import com.jetbrains.life_science.exception.section.SectionAlreadyExistsException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
import com.jetbrains.life_science.container.protocol.service.PublicProtocolService
import com.jetbrains.life_science.edit_record.protocol.service.maker.makeProtocolEditRecordInfo
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import com.jetbrains.life_science.util.UTCZone
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Sql("/scripts/initial_data.sql", "/scripts/edit_record/protocol_edit_record_data.sql")
@Transactional
class ProtocolEditRecordServiceTest {

    @Autowired
    lateinit var service: ProtocolEditRecordService

    @Autowired
    lateinit var credentialsService: CredentialsService

    @Autowired
    lateinit var sectionService: SectionService

    @Autowired
    lateinit var publicProtocolService: PublicProtocolService

    /**
     * Should get existing protocol edit record
     */
    @Test
    fun `get existing protocol_edit_record`() {
        // Prepare data
        val expectedRecordId = 1L
        val expectedProtocolId = 1L
        val expectedLastEditDate = LocalDateTime.of(2020, 12, 17, 0, 0)

        // Action
        val protocolEditRecord = service.get(expectedRecordId)

        // Assert
        assertEquals(expectedRecordId, protocolEditRecord.id)
        assertEquals(expectedProtocolId, protocolEditRecord.protocol.id)
        assertEquals(expectedLastEditDate, protocolEditRecord.lastEditDate)
    }

    /**
     * Should throw EditRecordNotFound exception
     */
    @Test
    fun `get non-existing protocol_edit_record`() {
        // Prepare data
        val recordId = 239L

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.get(recordId)
        }
    }

    /**
     * Should create new protocol edit record
     */
    @Test
    fun `create protocol_edit_record`() {
        // Prepare data
        val expectedRecordId = 3L
        val protocol = publicProtocolService.get(1L)
        val expectedDate = LocalDateTime.now(UTCZone)
        val info = makeProtocolEditRecordInfo(
            id = expectedRecordId,
            lastEditDate = expectedDate,
            protocol = protocol
        )

        // Action
        service.create(info)
        val protocolEditRecord = service.get(info.id)

        // Assert
        assertEquals(expectedRecordId, protocolEditRecord.id)
        assertEquals(protocol.id, protocolEditRecord.protocol.id)
        assertEquals(expectedDate, protocolEditRecord.lastEditDate)
    }

    /**
     * Should add section to protocol edit record
     */
    @Test
    fun `add section to existing protocol_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(2L)

        // Action
        service.addSection(recordId, section)
        val protocolEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, protocolEditRecord.id)
        assertSectionCreated(section.id, protocolEditRecord)
    }

    /**
     * Should add section to protocol edit record
     */
    @Test
    fun `add deleted section to existing protocol_edit_record`() {
        // Prepare data
        val recordId = 2L
        val section = sectionService.getById(3L)
        val prevprotocolEditRecord = service.get(recordId)

        // Pre-check
        assertEquals(recordId, prevprotocolEditRecord.id)
        assertSectionDeleted(section.id, prevprotocolEditRecord)

        // Action
        service.addSection(recordId, section)
        val protocolEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, protocolEditRecord.id)
        assertSectionNotDeleted(section.id, protocolEditRecord)
    }

    /**
     * Should throw SectionAlreadyExistsException
     */
    @Test
    fun `add existing in protocol section to existing protocol_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<SectionAlreadyExistsException> {
            service.addSection(recordId, section)
        }
    }

    /**
     * Should throw SectionAlreadyExistsException
     */
    @Test
    fun `add existing in edit_record section to existing protocol_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(3L)

        // Action & Assert
        assertThrows<SectionAlreadyExistsException> {
            service.addSection(recordId, section)
        }
    }

    /**
     * Should throw EditRecordNotFoundException
     */
    @Test
    fun `add created section to non-existing protocol_edit_record`() {
        // Prepare data
        val recordId = 239L
        val section = sectionService.getById(2L)

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.addSection(recordId, section)
        }
    }

    /**
     * Should add section to protocol edit record
     */
    @Test
    fun `delete section from protocol of existing protocol_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(1L)

        // Action
        service.deleteSection(recordId, section)
        val protocolEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, protocolEditRecord.id)
        assertSectionDeleted(section.id, protocolEditRecord)
    }

    /**
     * Should add section to protocol edit record
     */
    @Test
    fun `delete created section from existing protocol_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(3L)
        val prevprotocolEditRecord = service.get(recordId)

        // Pre-check
        assertEquals(recordId, prevprotocolEditRecord.id)
        assertContainsSection(section.id, prevprotocolEditRecord)

        // Action
        service.deleteSection(recordId, section)
        val protocolEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, protocolEditRecord.id)
        assertSectionNotCreated(section.id, protocolEditRecord)
    }

    /**
     * Should throw SectionAlreadyExistsException
     */
    @Test
    fun `delete deleted section from existing protocol_edit_record`() {
        // Prepare data
        val recordId = 2L
        val section = sectionService.getById(3L)

        // Action & Assert
        assertThrows<SectionAlreadyDeletedException> {
            service.deleteSection(recordId, section)
        }
    }

    /**
     * Should throw SectionNotFoundException
     */
    @Test
    fun `delete non-existing section from existing protocol_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(2L)

        // Action & Assert
        assertThrows<SectionNotFoundException> {
            service.deleteSection(recordId, section)
        }
    }

    /**
     * Should throw EditRecordNotFoundException
     */
    @Test
    fun `delete section from non-existing protocol_edit_record`() {
        // Prepare data
        val recordId = 239L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.deleteSection(recordId, section)
        }
    }

    /**
     * Should clear list of created and list of deleted sections
     */
    @Test
    fun `clear existing protocol_edit_record`() {
        // Prepare data
        val recordId = 1L
        val expectedprotocolId = service.get(recordId).protocol.id

        // Action
        service.clear(recordId)
        val protocolEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, protocolEditRecord.id)
        assertEquals(expectedprotocolId, protocolEditRecord.protocol.id)
        assertTrue(protocolEditRecord.deletedSections.isEmpty())
        assertTrue(protocolEditRecord.createdSections.isEmpty())
    }

    /**
     * Should throw EditRecordNotFound exception
     */
    @Test
    fun `clear non-existing protocol_edit_record`() {
        // Prepare data
        val recordId = 239L

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.clear(recordId)
        }
    }

    private fun assertSectionCreated(sectionId: Long, protocolEditRecord: ProtocolEditRecord) {
        assertTrue(protocolEditRecord.createdSections.any { it.id == sectionId })
    }

    private fun assertSectionNotCreated(sectionId: Long, protocolEditRecord: ProtocolEditRecord) {
        assertTrue(protocolEditRecord.createdSections.all { it.id != sectionId })
    }

    private fun assertSectionDeleted(sectionId: Long, protocolEditRecord: ProtocolEditRecord) {
        assertTrue(protocolEditRecord.deletedSections.any { it.id == sectionId })
    }

    private fun assertSectionNotDeleted(sectionId: Long, protocolEditRecord: ProtocolEditRecord) {
        assertTrue(protocolEditRecord.deletedSections.all { it.id != sectionId })
    }

    private fun assertContainsSection(sectionId: Long, protocolEditRecord: ProtocolEditRecord) {
        assertTrue(
            protocolEditRecord.createdSections.any { it.id == sectionId } ||
                protocolEditRecord.protocol.sections.any { it.id == sectionId }
        )
    }
}
