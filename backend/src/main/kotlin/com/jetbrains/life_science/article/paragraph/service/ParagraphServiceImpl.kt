package com.jetbrains.life_science.article.paragraph.service

import com.jetbrains.life_science.article.paragraph.entity.Paragraph
import com.jetbrains.life_science.article.paragraph.factory.ParagraphFactory
import com.jetbrains.life_science.article.paragraph.repository.ParagraphRepository
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.exception.ParagraphNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ParagraphServiceImpl(
    val repository: ParagraphRepository,
    val factory: ParagraphFactory,
) : ParagraphService {

    @Autowired
    lateinit var sectionService: SectionService

    override fun deleteBySectionId(sectionId: Long) {
        repository.deleteAllBySectionId(sectionId)
    }

    override fun findAllBySectionId(sectionId: Long): List<Paragraph> {
        return repository.findAllBySectionId(sectionId)
    }

    override fun findById(paragraphId: String?): Paragraph {
        if (paragraphId == null) {
            throw ParagraphNotFoundException("Paragraph not found by id: $paragraphId")
        }
        checkArticleExists(paragraphId)
        return repository.findById(paragraphId).get()
    }

    override fun createCopiesBySection(origin: Section, newSection: Section) {
        val articles = repository.findAllBySectionId(origin.id)
        articles.forEach { originArticle -> copy(originArticle, newSection) }
    }

    private fun copy(originParagraph: Paragraph, newSection: Section) {
        val copy = factory.copy(originParagraph)
        copy.sectionId = newSection.id
        repository.save(copy)
    }

    override fun create(info: ParagraphInfo): Paragraph {
        sectionService.checkExistsById(info.sectionId)
        val paragraph = factory.create(info)
        return repository.save(paragraph)
    }

    override fun update(info: ParagraphInfo): Paragraph {
        val paragraph = findById(info.id)
        factory.setParams(paragraph, info)
        return repository.save(paragraph)
    }

    override fun updateText(id: String, text: String): Paragraph {
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
            throw ParagraphNotFoundException("Paragraph not found by id: $id")
        }
    }
}
