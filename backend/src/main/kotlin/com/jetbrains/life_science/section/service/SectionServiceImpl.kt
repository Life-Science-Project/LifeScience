package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.content.publish.dto.ContentCreationToInfoAdapter
import com.jetbrains.life_science.content.publish.service.ContentService
import com.jetbrains.life_science.content.version.service.ContentVersionService
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
import com.jetbrains.life_science.exception.section.SectionAlreadyArchivedException
import com.jetbrains.life_science.exception.section.SectionAlreadyPublishedException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.factory.SectionFactory
import com.jetbrains.life_science.section.repository.SectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SectionServiceImpl(
    val factory: SectionFactory,
    val repository: SectionRepository,
) : SectionService {

    @Autowired
    lateinit var contentService: ContentService

    @Autowired
    lateinit var contentVersionService: ContentVersionService

    @Transactional
    override fun create(info: SectionInfo): Section {
        var section = factory.create(info)
        section = repository.save(section)
        val innerContent = info.contentInfo
        if (innerContent != null) {
            contentVersionService.create(ContentCreationToInfoAdapter(section.id, innerContent))
        }
        return section
    }

    @Transactional
    override fun deleteById(id: Long) {
        throwNotExist(id)
        val section = repository.findById(id).orElseThrow()
        contentService.deleteBySectionId(id)
        // Deleting row in database
        repository.delete(section)
    }

    override fun getById(id: Long): Section {
        throwNotExist(id)
        return repository.findById(id).get()
    }

    override fun existsById(id: Long): Boolean {
        return repository.existsById(id)
    }

    private fun throwNotExist(id: Long) {
        if (!existsById(id)) {
            throw SectionNotFoundException("Section not found by id: $id")
        }
    }

    @Transactional
    override fun update(info: SectionInfo): Section {
        val section = getById(info.id)
        factory.setParams(section, info)
        return section
    }

    override fun publish(sectionId: Long) {
        val section = getById(sectionId)
        if (!section.published) {
            contentService.publishBySectionId(sectionId)
            section.published = true
            repository.save(section)
        } else {
            throw SectionAlreadyPublishedException()
        }
    }

    override fun archive(sectionId: Long) {
        val section = getById(sectionId)
        if (section.published) {
            contentVersionService.archiveBySectionId(sectionId)
            section.published = false
            repository.save(section)
        } else {
            throw SectionAlreadyArchivedException()
        }
    }
}
