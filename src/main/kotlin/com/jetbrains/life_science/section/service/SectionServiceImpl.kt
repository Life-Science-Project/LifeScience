package com.jetbrains.life_science.section.service

import com.jetbrains.life_science.content.publish.service.ContentInfo
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
    override fun create(info: SectionCreationInfo): Section {
        val section = factory.create(info)
        moveOrdersOnCreate(info.prevSection, info.allSections) { repository.save(it) }
        return repository.save(section)
    }

    // Adds all the sections from the list in reversed order (to save the original order of sections without prevSection)
    @Transactional
    override fun createMany(infos: List<SectionCreationInfo>, existingSections: List<Section>): List<Section> {
        val allSections = existingSections.toMutableList()
        val currentSections = mutableListOf<Section>()
        infos.asReversed().forEach {
            val section = factory.create(it)
            moveOrdersOnCreate(it.prevSection, allSections)
            currentSections.add(repository.save(section))
            allSections.add(section)
        }
        return currentSections.reversed()
    }

    private fun moveOrdersOnCreate(
        previous: Section?,
        allSections: List<Section>,
        afterChange: (Section) -> Unit = {}
    ) {
        if (previous == null) {
            allSections
        } else {
            allSections.filter { it.order > previous.order }
        }
            .onEach { it.order++ }
            .forEach { afterChange(it) }
    }

    @Transactional
    override fun deleteById(id: Long, allSections: List<Section>) {
        val section = getById(id)
        moveOrdersOnDelete(section.id, section.order, allSections)
        contentService.deleteBySectionId(id)
        repository.deleteById(id)
    }

    private fun moveOrdersOnDelete(deletedId: Long, oldOrder: Int, allSections: List<Section>) {
        allSections.filter { it.order > oldOrder && it.id != deletedId }
            .onEach { it.order-- }
            .forEach { repository.save(it) }
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
            throw SectionNotFoundException(id)
        }
    }

    @Transactional
    override fun update(section: Section, info: SectionInfo): Section {
        if (section.published) {
            throw SectionAlreadyPublishedException()
        }
        factory.setParams(section, info)
        val saved = repository.save(section)
        moveOrdersOnUpdate(section, info.allSections)

        val contentInfo = SectionInfoToContentInfoAdapter(section.id, info.content)
        contentVersionService.updateOrCreateIfNotExists(contentInfo)
        return saved
    }

    class SectionInfoToContentInfoAdapter(
        override var sectionId: Long,
        override var text: String
    ) : ContentInfo {
        override var references: List<String> = emptyList()
        override var tags: List<String> = emptyList()
    }

    private fun moveOrdersOnUpdate(section: Section, allSections: List<Section>) {
        allSections.filter { it.id != section.id && it.order >= section.order }
            .onEach { it.order++ }
            .forEach { repository.save(it) }
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
