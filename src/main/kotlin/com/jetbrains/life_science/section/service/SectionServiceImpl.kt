package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.content.publish.service.ContentService
import com.jetbrains.life_science.content.version.service.ContentVersionService
import com.jetbrains.life_science.exception.section.SectionNotFoundException
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
        info.contentInfo.sectionId = section.id
        contentVersionService.create(info.contentInfo)
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
        if (!section.published) {
            factory.setParams(section, info)
            info.contentInfo.sectionId = info.id
            contentVersionService.update(info.contentInfo)
            return section
        } else {
            throw SectionAlreadyPublishedException()
        }
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
