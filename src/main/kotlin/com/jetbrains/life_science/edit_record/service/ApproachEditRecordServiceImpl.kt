package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.factory.ApproachEditRecordFactory
import com.jetbrains.life_science.edit_record.repository.ApproachEditRecordRepository
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Service

@Service
class ApproachEditRecordServiceImpl(
    val repository: ApproachEditRecordRepository,
    val factory: ApproachEditRecordFactory
) : ApproachEditRecordService {

    override fun get(id: Long): ApproachEditRecord {
        return repository.findById(id).orElseThrow {
            EditRecordNotFoundException("ApproachEditRecord not found by id: $id")
        }
    }

    override fun create(info: ApproachEditRecordInfo): ApproachEditRecord {
        val approachEditRecord = factory.create(info)
        return repository.save(approachEditRecord)
    }

    override fun addSection(id: Long, section: Section): ApproachEditRecord {
        val approachEditRecord = get(id)
        factory.addSection(approachEditRecord, section)
        return repository.save(approachEditRecord)
    }

    override fun deleteSection(id: Long, section: Section): ApproachEditRecord {
        val approachEditRecord = get(id)
        factory.deleteSection(approachEditRecord, section)
        return repository.save(approachEditRecord)
    }
}
