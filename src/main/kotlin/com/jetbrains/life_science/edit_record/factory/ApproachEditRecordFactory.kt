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

    fun setCurrentTimeToLastEditDate(approachEditRecord: ApproachEditRecord) {
        approachEditRecord.lastEditDate = LocalDateTime.now(ZoneId.of("UTC"))
    }
}
