package com.jetbrains.life_science.edit_record.approach.service

import com.jetbrains.life_science.approach.service.PublicApproachService
import com.jetbrains.life_science.edit_record.approach.service.maker.makeApproachEditRecordInfo
import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordService
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId

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
        val expectedDate = LocalDateTime.now(ZoneId.of("UTC"))
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
    fun `add created section to existing approach_edit_record`() {
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
    fun `delete created section from existing approach_edit_record`() {
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
    fun `delete created section from non-existing approach_edit_record`() {
        // Prepare data
        val recordId = 239L
        val section = sectionService.getById(1L)

        // Action & Assert
        assertThrows<EditRecordNotFoundException> {
            service.deleteSection(recordId, section)
        }
    }

    @Test
    fun `clear approach_edit_record`() {
        // Prepare data
        val recordId = 1L

        // Action
        service.clear(recordId)
        val approachEditRecord = service.get(recordId)

        // Assert
        assertEquals(recordId, approachEditRecord.id)
        assertTrue(approachEditRecord.deletedSections.isEmpty())
        assertTrue(approachEditRecord.createdSections.isEmpty())
    }

    private fun assertSectionCreated(sectionId: Long, approachEditRecord: ApproachEditRecord) {
        assertTrue(approachEditRecord.createdSections.any { it.id == sectionId })
    }

    private fun assertSectionDeleted(sectionId: Long, approachEditRecord: ApproachEditRecord) {
        assertTrue(approachEditRecord.deletedSections.any { it.id == sectionId })
    }
}
