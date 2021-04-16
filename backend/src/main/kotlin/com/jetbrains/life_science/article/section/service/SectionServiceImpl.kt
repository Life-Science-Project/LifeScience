package com.jetbrains.life_science.article.section.service

import com.jetbrains.life_science.article.paragraph.service.ParagraphService
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.factory.SectionFactory
import com.jetbrains.life_science.article.section.repository.SectionRepository
import com.jetbrains.life_science.article.section.search.service.SectionSearchUnitService
import com.jetbrains.life_science.exception.SectionNotFoundException
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SectionServiceImpl(
    val factory: SectionFactory,
    val repository: SectionRepository,
    val searchService: SectionSearchUnitService
) : SectionService {

    @Autowired
    lateinit var articleVersionService: ArticleVersionService

    @Autowired
    lateinit var paragraphService: ParagraphService

    @Transactional
    override fun create(info: SectionInfo): Section {
        val article = articleVersionService.getById(info.articleVersionId)
        var section = factory.create(info.name, info.description, article)
        // Creating row in database
        section = repository.save(section)
        return section
    }

    @Transactional
    override fun deleteById(id: Long) {
        checkExistsById(id)
        val section = repository.findById(id).orElseThrow()
        paragraphService.deleteBySectionId(id)
        // Deleting row in database
        repository.delete(section)
    }

    @Transactional
    override fun createCopiesByArticle(article: ArticleVersion, newArticle: ArticleVersion) {
        val sections = repository.findAllByArticleVersion(article)
        sections.forEach { section -> createCopy(section, newArticle) }
    }

    override fun deleteSearchUnits(oldSections: List<Section>) {
        oldSections.forEach { searchService.delete(it.id) }
    }

    override fun createSearchUnits(newSections: List<Section>) {
        newSections.forEach { searchService.create(it) }
    }

    private fun createCopy(origin: Section, newArticle: ArticleVersion) {
        val copy = factory.copy(origin)
        copy.articleVersion = newArticle
        paragraphService.createCopiesBySection(origin, copy)
        repository.save(copy)
    }

    override fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw SectionNotFoundException("Section not found by id: $id")
        }
    }

    @Transactional
    override fun update(sectionId: Long, info: SectionInfo): Section {
        val section = getById(sectionId)
        val version = articleVersionService.getById(info.articleVersionId)
        factory.setParams(section, info, version)
        searchService.update(section)
        return section
    }

    override fun getById(id: Long): Section {
        checkExistsById(id)
        return repository.getOne(id)
    }

    override fun getByVersionId(versionId: Long): List<Section> {
        return repository.findAllByArticleVersionId(versionId)
    }
}
