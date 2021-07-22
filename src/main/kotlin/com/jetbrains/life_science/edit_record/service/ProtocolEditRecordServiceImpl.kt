package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Service

@Service
class ProtocolEditRecordServiceImpl : ProtocolEditRecordService {
    override fun get(id: Long): ProtocolEditRecord {
        TODO("Not yet implemented")
    }

    override fun create(info: ProtocolEditRecordInfo): ProtocolEditRecordInfo {
        TODO("Not yet implemented")
    }

    override fun addSection(id: Long, section: Section): ProtocolEditRecord {
        TODO("Not yet implemented")
    }

    override fun deleteSection(id: Long, section: Section): ProtocolEditRecord {
        TODO("Not yet implemented")
    }

    override fun clear(id: Long) {
        TODO("Not yet implemented")
    }
}
