package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.section.entity.Section

interface ProtocolEditRecordService {

    fun get(id: Long): ProtocolEditRecord

    fun create(info: ProtocolEditRecordInfo): ProtocolEditRecord

    fun addSection(id: Long, section: Section): ProtocolEditRecord

    fun deleteSection(id: Long, section: Section): ProtocolEditRecord

    fun clear(id: Long)
}
