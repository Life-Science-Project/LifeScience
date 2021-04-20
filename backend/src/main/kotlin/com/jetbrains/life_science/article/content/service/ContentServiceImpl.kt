package com.jetbrains.life_science.article.content.service

import com.jetbrains.life_science.article.content.entity.Content
import com.jetbrains.life_science.article.content.factory.ContentFactory
import com.jetbrains.life_science.article.content.repository.ContentRepository
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.exception.ContentAlreadyExistsException
import com.jetbrains.life_science.exception.ContentNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContentServiceImpl(
    val repository: ContentRepository,
    val factory: ContentFactory,
) : ContentService {

    @Autowired
    lateinit var sectionService: SectionService

    override fun deleteBySectionId(sectionId: Long) {
        repository.deleteAllBySectionId(sectionId)
    }

    override fun findBySectionId(sectionId: Long): Content? {
        return repository.findBySectionId(sectionId)
    }

    override fun findById(contentId: String?): Content {
        if (contentId == null) {
            throw ContentNotFoundException("Paragraph not found by id: $contentId")
        }
        checkArticleExists(contentId)
        return repository.findById(contentId).get()
    }

    override fun createCopyBySection(origin: Section, newSection: Section) {
        val content = repository.findBySectionId(origin.id)
        content?.let { copy(it, newSection) }
    }

    private fun copy(originContent: Content, newSection: Section) {
        val copy = factory.copy(originContent)
        copy.sectionId = newSection.id
        repository.save(copy)
    }

    override fun create(info: ContentInfo): Content {
        sectionService.checkExistsById(info.sectionId)
        if (repository.existsBySectionId(info.sectionId)) {
            throw ContentAlreadyExistsException("Content already exists is section with id: ${info.sectionId}")
        }
        val content = factory.create(info)
        return repository.save(content)
    }

    override fun update(info: ContentInfo): Content {
        val content = findById(info.id)
        factory.setParams(content, info)
        return repository.save(content)
    }

    override fun updateText(id: String, text: String): Content {
        checkArticleExists(id)
        repository.updateText(id, text)
        return repository.findById(id).get()
    }

    override fun delete(id: String) {
        checkArticleExists(id)
        repository.deleteById(id)
    }

    private fun checkArticleExists(id: String) {
        if (!repository.existsById(id)) {
            throw ContentNotFoundException("Content not found by id: $id")
        }
    }
}
