package com.jetbrains.life_science.edit_record.factory

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.service.ApproachEditRecordInfo
import org.springframework.stereotype.Component
import java.time.LocalDateTime

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

    fun changeLastEditDate(ApproachEditRecord: ApproachEditRecord, lastEditDate: LocalDateTime) {
        ApproachEditRecord.lastEditDate = lastEditDate
    }
}
