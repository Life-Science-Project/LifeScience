package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.section.entity.Section

interface ApproachEditRecordService {

    fun get(id: Long): ApproachEditRecord

    fun create(info: ApproachEditRecordInfo): ApproachEditRecord

    fun addSection(id: Long, section: Section): ApproachEditRecord

    fun deleteSection(id: Long, section: Section): ApproachEditRecord

    fun clear(id: Long)
}
