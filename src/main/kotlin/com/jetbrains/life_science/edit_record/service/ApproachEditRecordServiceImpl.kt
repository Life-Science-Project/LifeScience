package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.factory.ApproachEditRecordFactory
import com.jetbrains.life_science.edit_record.repository.ApproachEditRecordRepository
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.exception.section.SectionAlreadyDeletedException
import com.jetbrains.life_science.exception.section.SectionAlreadyExistsException
import com.jetbrains.life_science.exception.section.SectionNotFoundException
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
        when {
            approachEditRecord.containsDeletedSectionById(section.id) -> {
                approachEditRecord.deletedSections.remove(section)
            }
            approachEditRecord.containsCreatedSectionById(section.id)
                || approachEditRecord.containsSectionById(section.id) -> {
                throw SectionAlreadyExistsException(
                    "Section with id ${section.id} is already exists in createdSections or approach"
                )
            }
            else -> approachEditRecord.createdSections.add(section)
        }
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        return repository.save(approachEditRecord)
    }

    override fun deleteSection(id: Long, section: Section): ApproachEditRecord {
        val approachEditRecord = get(id)
        when {
            approachEditRecord.containsDeletedSectionById(section.id) -> {
                throw SectionAlreadyDeletedException(
                    "Section with id ${section.id} is already exists in deletedSections"
                )
            }
            approachEditRecord.containsSectionById(section.id) -> {
                approachEditRecord.deletedSections.add(section)
            }
            approachEditRecord.containsCreatedSectionById(section.id) -> {
                approachEditRecord.createdSections.remove(section)
            }
            else -> {
                throw SectionNotFoundException(section.id)
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
