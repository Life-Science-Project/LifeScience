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
        if (section in approachEditRecord.createdSections) {
            return
        }
        approachEditRecord.lastEditDate = LocalDateTime.now(ZoneId.of("UTC"))
        if (section in approachEditRecord.deletedSections) {
            approachEditRecord.deletedSections.remove(section)
            return
        }
        approachEditRecord.createdSections.add(section)
    }

    fun deleteSection(approachEditRecord: ApproachEditRecord, section: Section) {
        if (section in approachEditRecord.deletedSections) {
            return
        }
        approachEditRecord.lastEditDate = LocalDateTime.now(ZoneId.of("UTC"))
        if (section in approachEditRecord.createdSections) {
            approachEditRecord.createdSections.remove(section)
            return
        }
        approachEditRecord.deletedSections.add(section)
    }
}
