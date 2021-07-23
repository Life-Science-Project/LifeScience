package com.jetbrains.life_science.edit_record.service

import com.jetbrains.life_science.edit_record.entity.ProtocolEditRecord
import com.jetbrains.life_science.edit_record.factory.ProtocolEditRecordFactory
import com.jetbrains.life_science.edit_record.repository.ProtocolEditRecordRepository
import com.jetbrains.life_science.exception.not_found.EditRecordNotFoundException
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
import com.jetbrains.life_science.exception.section.SectionAlreadyDeletedException
import com.jetbrains.life_science.exception.section.SectionAlreadyExistsException
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Service

@Service
class ProtocolEditRecordServiceImpl(
    val repository: ProtocolEditRecordRepository,
    val factory: ProtocolEditRecordFactory
) : ProtocolEditRecordService {

    override fun get(id: Long): ProtocolEditRecord {
        return repository.findById(id).orElseThrow {
            throw EditRecordNotFoundException("ProtocolEditRecord not found by id: $id")
        }
    }

    override fun create(info: ProtocolEditRecordInfo): ProtocolEditRecord {
        val protocolEditRecord = factory.create(info)
        return repository.save(protocolEditRecord)
    }

    override fun addSection(id: Long, section: Section): ProtocolEditRecord {
        val protocolEditRecord = get(id)
        when {
            containsById(section.id, protocolEditRecord.deletedSections) -> {
                protocolEditRecord.deletedSections.remove(section)
            }
            containsById(section.id, protocolEditRecord.createdSections) ||
                containsById(section.id, protocolEditRecord.protocol.sections) -> {
                throw SectionAlreadyExistsException(
                    "Section with id ${section.id} is already exists in createdSections or protocol"
                )
            }
            else -> protocolEditRecord.createdSections.add(section)
        }
        factory.setCurrentTimeToLastEditDate(protocolEditRecord)
        return repository.save(protocolEditRecord)
    }

    override fun deleteSection(id: Long, section: Section): ProtocolEditRecord {
        val protocolEditRecord = get(id)
        when {
            containsById(section.id, protocolEditRecord.deletedSections) -> {
                throw SectionAlreadyDeletedException(
                    "Section with id ${section.id} is already exists in deletedSections of protocolEditRecord"
                )
            }
            containsById(section.id, protocolEditRecord.protocol.sections) -> {
                protocolEditRecord.deletedSections.add(section)
            }
            containsById(section.id, protocolEditRecord.createdSections) -> {
                protocolEditRecord.createdSections.remove(section)
            }
            else -> {
                throw SectionNotFoundException(
                    "Section with id ${section.id} not found in deletedSections of protocolEditRecord"
                )
            }
        }
        factory.setCurrentTimeToLastEditDate(protocolEditRecord)
        return repository.save(protocolEditRecord)
    }

    override fun clear(id: Long) {
        val protocolEditRecord = get(id)
        protocolEditRecord.createdSections.clear()
        protocolEditRecord.deletedSections.clear()
        factory.setCurrentTimeToLastEditDate(protocolEditRecord)
        repository.save(protocolEditRecord)
    }

    private fun containsById(id: Long, list: List<Section>): Boolean {
        return list.any { it.id == id }
    }
}
