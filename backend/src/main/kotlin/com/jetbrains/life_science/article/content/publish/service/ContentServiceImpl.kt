package com.jetbrains.life_science.article.content.publish.service

import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.factory.ContentFactory
import com.jetbrains.life_science.article.content.publish.repository.ContentRepository
import com.jetbrains.life_science.article.content.version.service.ContentVersionService
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.exception.not_found.ContentNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContentServiceImpl(
    val repository: ContentRepository,
    val factory: ContentFactory
) : ContentService {

    @Autowired
    lateinit var contentVersionService: ContentVersionService

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
            throw ContentNotFoundException("Content not found by null id")
        }
        checkArticleExists(contentId)
        return repository.findById(contentId).get()
    }

    override fun publishBySectionId(sectionId: Long) {
        val content = contentVersionService.findBySectionId(sectionId)
        if (content != null) {
            val copy = factory.copy(content)
            repository.save(copy)
            contentVersionService.deleteBySectionId(sectionId)
        }
    }

    override fun createCopyBySection(origin: Section, newSection: Section) {
        val content = repository.findBySectionId(origin.id)
        content?.let { copy(it, newSection) }
    }

    private fun copy(originContent: Content, newSection: Section) {
        val copy = factory.copy(originContent)
        copy.sectionId = newSection.id
        copy.versionId = newSection.articleVersion.id
        contentVersionService.saveCopy(copy)
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
