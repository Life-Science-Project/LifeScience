package com.jetbrains.life_science.article.content.version.service

import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.factory.ContentFactory
import com.jetbrains.life_science.article.content.publish.service.ContentInfo
import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.article.version.entity.State
import com.jetbrains.life_science.exception.not_found.ContentNotFoundException
import com.jetbrains.life_science.exception.request.ContentAlreadyExistsException
import com.jetbrains.life_science.exception.request.ContentIsNotEditableException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContentVersionServiceImpl(
    val repository: ContentVersionRepository,
    val factory: ContentFactory
) : ContentVersionService {

    @Autowired
    lateinit var contentService: ContentService

    @Autowired
    lateinit var sectionService: SectionService

    override fun create(info: ContentInfo): Content {
        val section = sectionService.getById(info.sectionId)
        validateContentNotExists(section)
        validateSectionIsEditable(section)
        val content = factory.create(info, section.articleVersion.id)
        return repository.save(content)
    }

    override fun saveCopy(copy: Content) {
        repository.save(copy)
    }

    override fun deleteBySectionId(sectionId: Long) {
        repository.deleteBySectionId(sectionId)
    }

    override fun findById(contentId: String?): Content {
        if (contentId == null) {
            throw ContentNotFoundException("Content not found by null id")
        }
        return repository.findById(contentId) ?: throw ContentNotFoundException("Content not found by id $contentId")
    }

    override fun archiveBySectionId(sectionId: Long) {
        val content = contentService.findBySectionId(sectionId)
        if (content != null) {
            val copy = factory.copy(content)
            repository.save(copy)
            contentService.deleteBySectionId(sectionId)
        }
    }

    override fun update(info: ContentInfo): Content {
        val content = findById(info.id)
        val oldSection = sectionService.getById(content.sectionId)
        val section = sectionService.getById(info.sectionId)

        validateSectionIsEditable(oldSection)
        validateSectionIsEditable(section)
        if (section.id != oldSection.id) {
            validateContentNotExists(section)
        }
        factory.setParams(content, info, section.articleVersion.id)
        return repository.save(content)
    }

    private fun validateContentNotExists(section: Section) {
        if (repository.findBySectionId(section.id) != null) {
            throw ContentAlreadyExistsException("Content already exists")
        }
    }

    private fun validateSectionIsEditable(section: Section) {
        if (section.articleVersion.state != State.EDITING) {
            throw ContentIsNotEditableException("Content is not editable")
        }
    }

    override fun findBySectionId(sectionId: Long): Content? {
        return repository.findBySectionId(sectionId)
    }

    override fun delete(contentId: String) {
        repository.deleteById(contentId)
    }
}
