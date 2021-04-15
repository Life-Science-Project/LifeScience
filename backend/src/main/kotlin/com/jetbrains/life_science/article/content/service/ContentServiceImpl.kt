package com.jetbrains.life_science.article.content.service

import com.jetbrains.life_science.article.content.entity.Content
import com.jetbrains.life_science.article.content.factory.ContentFactory
import com.jetbrains.life_science.article.content.repository.ContentRepository
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.exception.ContainerNotFoundException
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

    override fun findAllBySectionId(sectionId: Long): List<Content> {
        return repository.findAllBySectionId(sectionId)
    }

    override fun findById(contentId: String): Content {
        checkArticleExists(contentId)
        return repository.findById(contentId).get()
    }

    override fun createCopiesBySection(origin: Section, newSection: Section) {
        val articles = repository.findAllBySectionId(origin.id)
        articles.forEach { originArticle -> copy(originArticle, newSection) }
    }

    private fun copy(originContent: Content, newSection: Section) {
        val copy = factory.copy(originContent)
        copy.sectionId = newSection.id
        repository.save(copy)
    }

    override fun create(info: ContentInfo): Content {
        sectionService.checkExistsById(info.sectionId)
        val article = factory.create(info)
        return repository.save(article)
    }

    override fun update(id: String, info: ContentInfo): Content {
        checkArticleExists(id)
        return create(info)
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
            throw ContainerNotFoundException("Content not found by id: $id")
        }
    }
}