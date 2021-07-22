package com.jetbrains.life_science.edit_record.factory

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.edit_record.service.ProtocolEditRecordInfo
import com.jetbrains.life_science.util.UTCZone
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ProtocolEditRecordFactory {
    fun create(info: ProtocolEditRecordInfo): ProtocolEditRecord {
        return ProtocolEditRecord(
            id = 0,
            lastEditDate = info.lastEditDate,
            protocol = info.protocol,
            createdSections = mutableListOf(),
            deletedSections = mutableListOf()
        )
    }

    fun setCurrentTimeToLastEditDate(protocolEditRecord: ProtocolEditRecord) {
        protocolEditRecord.lastEditDate = LocalDateTime.now(UTCZone)
    }
}
