package com.jetbrains.life_science.edit_record.approach.service

import com.jetbrains.life_science.edit_record.service.ApproachEditRecordService
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.section.service.SectionService
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.junit.jupiter.api.Assertions.assertEquals
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
}
