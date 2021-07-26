package com.jetbrains.life_science.edit_record.approach.service

import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.edit_record.approach.service.maker.makeApproachEditRecordInfo
import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordService
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.exception.section.SectionAlreadyDeletedException
import com.jetbrains.life_science.exception.section.SectionAlreadyExistsException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
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
@Sql("/scripts/initial_data.sql", "/scripts/edit_record/approach_edit_record_data.sql")
@Transactional
class ApproachEditRecordServiceTest {

    @Autowired
    lateinit var service: ApproachEditRecordService

    @Autowired
    lateinit var credentialsService: CredentialsService

    @Autowired
    lateinit var sectionService: SectionService

    @Autowired
    lateinit var publicApproachService: PublicApproachService

    /**
     * Should get existing approach edit record
     */
    @Test
    fun `get existing approach_edit_record`() {
        // Prepare data
        val expectedRecordId = 1L
        val expectedApproachId = 1L
        val expectedLastEditDate = LocalDateTime.of(2020, 12, 17, 0, 0)

        // Action
        val approachEditRecord = service.get(expectedRecordId)

        // Assert
        assertEquals(expectedRecordId, approachEditRecord.id)
        assertEquals(expectedApproachId, approachEditRecord.approach.id)
        assertEquals(expectedLastEditDate, approachEditRecord.lastEditDate)
    }

    /**
     * Should throw EditRecordNotFound exception
     */
    @Test
    fun `get non-existing approach_edit_record`() {
        // Prepare data
        val recordId = 239L

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.get(recordId)
        }
    }

    /**
     * Should create new approach edit record
     */
    @Test
    fun `create approach_edit_record`() {
        // Prepare data
        val expectedRecordId = 3L
        val approach = publicApproachService.get(1L)
        val expectedDate = LocalDateTime.now(UTCZone)
        val info = makeApproachEditRecordInfo(
            id = expectedRecordId,
            lastEditDate = expectedDate,
            approach = approach
        )

        // Action
        service.create(info)
        val approachEditRecord = service.get(info.id)

        // Assert
        assertEquals(expectedRecordId, approachEditRecord.id)
        assertEquals(approach.id, approachEditRecord.approach.id)
        assertEquals(expectedDate, approachEditRecord.lastEditDate)
    }

    /**
     * Should add section to approach edit record
     */
    @Test
    fun `add section to existing approach_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(2L)

        // Action
        service.addSection(recordId, section)
        val approachEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, approachEditRecord.id)
        assertSectionCreated(section.id, approachEditRecord)
    }

    /**
     * Should add section to approach edit record
     */
    @Test
    fun `add deleted section to existing approach_edit_record`() {
        // Prepare data
        val recordId = 2L
        val section = sectionService.getById(3L)
        val prevApproachEditRecord = service.get(recordId)

        // Pre-check
        assertEquals(recordId, prevApproachEditRecord.id)
        assertSectionDeleted(section.id, prevApproachEditRecord)

        // Action
        service.addSection(recordId, section)
        val approachEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, approachEditRecord.id)
        assertSectionNotDeleted(section.id, approachEditRecord)
    }

    /**
     * Should throw SectionAlreadyExistsException
     */
    @Test
    fun `add existing in approach section to existing approach_edit_record`() {
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
    fun `add existing in edit_record section to existing approach_edit_record`() {
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
    fun `add created section to non-existing approach_edit_record`() {
        // Prepare data
        val recordId = 239L
        val section = sectionService.getById(2L)

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.addSection(recordId, section)
        }
    }

    /**
     * Should add section to approach edit record
     */
    @Test
    fun `delete section from approach of existing approach_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(1L)

        // Action
        service.deleteSection(recordId, section)
        val approachEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, approachEditRecord.id)
        assertSectionDeleted(section.id, approachEditRecord)
    }

    /**
     * Should add section to approach edit record
     */
    @Test
    fun `delete created section from existing approach_edit_record`() {
        // Prepare data
        val recordId = 1L
        val section = sectionService.getById(3L)
        val prevApproachEditRecord = service.get(recordId)

        // Pre-check
        assertEquals(recordId, prevApproachEditRecord.id)
        assertContainsSection(section.id, prevApproachEditRecord)

        // Action
        service.deleteSection(recordId, section)
        val approachEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, approachEditRecord.id)
        assertSectionNotCreated(section.id, approachEditRecord)
    }

    /**
     * Should throw SectionAlreadyExistsException
     */
    @Test
    fun `delete deleted section from existing approach_edit_record`() {
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
    fun `delete non-existing section from existing approach_edit_record`() {
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
    fun `delete section from non-existing approach_edit_record`() {
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
    fun `clear existing approach_edit_record`() {
        // Prepare data
        val recordId = 1L
        val expectedApproachId = service.get(recordId).approach.id

        // Action
        service.clear(recordId)
        val approachEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, approachEditRecord.id)
        assertEquals(expectedApproachId, approachEditRecord.approach.id)
        assertTrue(approachEditRecord.deletedSections.isEmpty())
        assertTrue(approachEditRecord.createdSections.isEmpty())
    }

    /**
     * Should throw EditRecordNotFound exception
     */
    @Test
    fun `clear non-existing approach_edit_record`() {
        // Prepare data
        val recordId = 239L

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.clear(recordId)
        }
    }

    private fun assertSectionCreated(sectionId: Long, approachEditRecord: ApproachEditRecord) {
        assertTrue(approachEditRecord.createdSections.any { it.id == sectionId })
    }

    private fun assertSectionNotCreated(sectionId: Long, approachEditRecord: ApproachEditRecord) {
        assertTrue(approachEditRecord.createdSections.all { it.id != sectionId })
    }

    private fun assertSectionDeleted(sectionId: Long, approachEditRecord: ApproachEditRecord) {
        assertTrue(approachEditRecord.deletedSections.any { it.id == sectionId })
    }

    private fun assertSectionNotDeleted(sectionId: Long, approachEditRecord: ApproachEditRecord) {
        assertTrue(approachEditRecord.deletedSections.all { it.id != sectionId })
    }

    private fun assertContainsSection(sectionId: Long, approachEditRecord: ApproachEditRecord) {
        assertTrue(
            approachEditRecord.createdSections.any { it.id == sectionId } ||
                approachEditRecord.approach.sections.any { it.id == sectionId }
        )
    }
}
