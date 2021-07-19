package com.jetbrains.life_science.edit_record.factory

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordInfo
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class ApproachEditRecordFactory {
    fun create(info: ApproachEditRecordInfo): ApproachEditRecord {
        return ApproachEditRecord(
            id = 0,
            lastEditDate = info.lastEditDate,
            approach = info.approach,
            createdSections = mutableListOf(),
            deletedSections = mutableListOf()
        )
    }

    fun addSection(approachEditRecord: ApproachEditRecord, section: Section) {
        updateSections(approachEditRecord, section, true)
    }

    fun deleteSection(approachEditRecord: ApproachEditRecord, section: Section) {
        updateSections(approachEditRecord, section, false)
    }

    private fun updateSections(approachEditRecord: ApproachEditRecord, section: Section, add: Boolean) {
        val listToAdd = if (add) approachEditRecord.createdSections else approachEditRecord.deletedSections
        val listToDelete = if (add) approachEditRecord.deletedSections else approachEditRecord.createdSections

        if (section !in listToAdd) {
            return
        }
        approachEditRecord.lastEditDate = LocalDateTime.now(ZoneId.of("UTC"))
        if (section in listToDelete) {
            listToDelete.remove(section)
            return
        }
        listToAdd.add(section)
    }
}
