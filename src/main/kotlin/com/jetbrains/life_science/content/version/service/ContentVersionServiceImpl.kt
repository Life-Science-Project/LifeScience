package com.jetbrains.life_science.content.version.service

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.publish.factory.ContentFactory
import com.jetbrains.life_science.content.publish.service.ContentInfo
import com.jetbrains.life_science.content.publish.service.ContentService
import com.jetbrains.life_science.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.exception.not_found.ContentNotFoundException
import com.jetbrains.life_science.exception.request.ContentAlreadyExistsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContentVersionServiceImpl(
    val repository: ContentVersionRepository,
    val factory: ContentFactory
) : ContentVersionService {

    @Autowired
    lateinit var contentService: ContentService

    override fun updateOrCreateIfNotExists(info: ContentInfo) {
        val content = findBySectionId(info.sectionId)
        if (content != null) {
            update(content, info)
        } else {
            create(info)
        }
    }

    override fun create(info: ContentInfo): Content {
        validateContentNotExists(info.sectionId)
        val content = factory.create(info)
        return repository.save(content)
    }

    override fun findById(contentId: String?): Content {
        if (contentId == null) {
            throw ContentNotFoundException("Content not found by null id")
        }
        return repository.findById(contentId) ?: throw ContentNotFoundException("Content not found by id $contentId")
    }

    override fun update(info: ContentInfo): Content {
        val content = findBySectionId(info.sectionId)
        return update(content, info)
    }

    private fun update(
        content: Content?,
        info: ContentInfo
    ): Content {
        if (content != null) {
            factory.setParams(content, info)
        } else {
            throw ContentNotFoundException("Content not found by section id ${info.sectionId}")
        }
        return repository.save(content)
    }

    override fun delete(contentId: String) {
        repository.deleteById(contentId)
    }

    override fun findBySectionId(sectionId: Long): Content? {
        return repository.findBySectionId(sectionId)
    }

    override fun deleteBySectionId(sectionId: Long) {
        repository.deleteBySectionId(sectionId)
    }

    override fun archiveBySectionId(sectionId: Long) {
        val content = contentService.findBySectionId(sectionId)
        if (content != null) {
            val copy = factory.copy(content)
            repository.save(copy)
            contentService.deleteBySectionId(sectionId)
        }
    }

    private fun validateContentNotExists(sectionId: Long) {
        if (repository.findBySectionId(sectionId) != null) {
            throw ContentAlreadyExistsException("Content already exists")
        }
    }
}
