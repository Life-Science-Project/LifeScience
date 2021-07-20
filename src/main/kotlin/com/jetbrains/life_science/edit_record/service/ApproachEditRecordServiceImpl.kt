package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ApproachEditRecord
import com.jetbrains.life_science.edit_record.factory.ApproachEditRecordFactory
import com.jetbrains.life_science.edit_record.repository.ApproachEditRecordRepository
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
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
        if (section in approachEditRecord.createdSections) {
            return approachEditRecord
        }
        approachEditRecord.createdSections.add(section)
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        return repository.save(approachEditRecord)
    }

    override fun deleteSection(id: Long, section: Section): ApproachEditRecord {
        val approachEditRecord = get(id)
        if (section in approachEditRecord.deletedSections) {
            return approachEditRecord
        }
        approachEditRecord.deletedSections.add(section)
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        return repository.save(approachEditRecord)
    }

    override fun recoverDeletedSection(id: Long, section: Section): ApproachEditRecord {
        val approachEditRecord = get(id)
        if (section !in approachEditRecord.deletedSections) {
            throw SectionNotFoundException("There are no remote sections with id ${section.id}")
        }
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        approachEditRecord.deletedSections.remove(section)
        return repository.save(approachEditRecord)
    }

    override fun deleteCreatedSection(id: Long, section: Section): ApproachEditRecord {
        val approachEditRecord = get(id)
        if (section !in approachEditRecord.createdSections) {
            throw SectionNotFoundException("There are no created sections with id ${section.id}")
        }
        factory.setCurrentTimeToLastEditDate(approachEditRecord)
        approachEditRecord.createdSections.remove(section)
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
