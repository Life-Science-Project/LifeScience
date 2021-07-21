package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.factory.ApproachEditRecordFactory
import com.jetbrains.life_science.edit_record.repository.ApproachEditRecordRepository
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
import com.jetbrains.life_science.exception.section.SectionAlreadyExistsException
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
        if (section in approachEditRecord.deletedSections) {
            approachEditRecord.deletedSections.remove(section)
        } else if (section in approachEditRecord.createdSections ||
            section in approachEditRecord.approach.sections
        ) {
            throw SectionAlreadyExistsException(
                "Section with id ${section.id} is already exists in createdSections or approach"
            )
        } else {
            approachEditRecord.createdSections.add(section)
        }
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        return repository.save(approachEditRecord)
    }

    override fun deleteSection(id: Long, section: Section): ApproachEditRecord {
        val approachEditRecord = get(id)
        if (section in approachEditRecord.deletedSections) {
            throw SectionAlreadyExistsException("Section with id ${section.id} is already exists in deletedSections")
        }
        when (section) {
            in approachEditRecord.approach.sections -> {
                approachEditRecord.deletedSections.add(section)
            }
            in approachEditRecord.createdSections -> {
                approachEditRecord.createdSections.remove(section)
            }
            else -> {
                throw SectionNotFoundException("Section with id ${section.id} not found in deletedSections")
            }
        }
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        return repository.save(approachEditRecord)
    }

    override fun clear(id: Long) {
        val approachEditRecord = get(id)
        approachEditRecord.createdSections.clear()
        approachEditRecord.deletedSections.clear()
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        repository.save(approachEditRecord)
    }
}
