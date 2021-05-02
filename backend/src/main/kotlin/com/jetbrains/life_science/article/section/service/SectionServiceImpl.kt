package com.jetbrains.life_science.article.section.service

import com.jetbrains.life_science.article.content.publish.service.ContentService
import com.jetbrains.life_science.article.content.version.service.ContentVersionService
import com.jetbrains.life_science.article.section.entity.Section
import com.jetbrains.life_science.article.section.factory.SectionFactory
import com.jetbrains.life_science.article.section.repository.SectionRepository
import com.jetbrains.life_science.article.section.search.service.SectionSearchUnitService
import com.jetbrains.life_science.exception.not_found.SectionNotFoundException
import com.jetbrains.life_science.article.version.entity.ArticleVersion
import com.jetbrains.life_science.article.version.service.ArticleVersionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SectionServiceImpl(
    val factory: SectionFactory,
    val repository: SectionRepository,
    val searchService: SectionSearchUnitService,
) : SectionService {

    @Autowired
    lateinit var articleVersionService: ArticleVersionService

    @Autowired
    lateinit var contentService: ContentService

    @Autowired
    lateinit var contentVersionService: ContentVersionService

    @Transactional
    override fun create(info: SectionInfo): Section {
        val article = articleVersionService.getById(info.articleVersionId)
        var section = factory.create(info, article)
        // Creating row in database
        section = repository.save(section)
        return section
    }

    @Transactional
    override fun deleteById(id: Long) {
        checkExistsById(id)
        val section = repository.findById(id).orElseThrow()
        contentService.deleteBySectionId(id)
        // Deleting row in database
        repository.delete(section)
    }

    @Transactional
    override fun createCopiesByArticle(article: ArticleVersion, newArticle: ArticleVersion) {
        val sections = article.sections
        sections.forEach { section -> newArticle.sections.add(createCopy(section, newArticle)) }
    }

    override fun deleteSearchUnits(oldSections: List<Section>) {
        oldSections.forEach { searchService.delete(it.id) }
    }

    override fun publish(newSections: List<Section>) {
        newSections.forEach { section ->
            searchService.create(section)
            contentService.publishBySectionId(section.id)
        }
    }

    override fun archive(sections: List<Section>) {
        sections.forEach { section ->
            contentVersionService.archiveBySectionId(section.id)
        }
    }

    private fun createCopy(origin: Section, newArticle: ArticleVersion): Section {
        val copy = factory.copy(origin)
        copy.articleVersion = newArticle
        contentService.createCopyBySection(origin, copy)
        return repository.save(copy)
    }

    override fun checkExistsById(id: Long) {
        if (!repository.existsById(id)) {
            throw SectionNotFoundException("Section not found by id: $id")
        }
    }

    @Transactional
    override fun update(info: SectionInfo): Section {
        val section = getById(info.id)
        val version = articleVersionService.getById(info.articleVersionId)
        factory.setParams(section, info, version)
        return section
    }

    override fun getById(id: Long): Section {
        checkExistsById(id)
        return repository.findById(id).get()
    }

    override fun getByVersionId(versionId: Long): List<Section> {
        return repository.findAllByArticleVersionId(versionId)
    }
}
