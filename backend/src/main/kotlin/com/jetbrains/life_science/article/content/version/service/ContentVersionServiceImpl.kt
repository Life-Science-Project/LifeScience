package com.jetbrains.life_science.article.content.version.service

import com.jetbrains.life_science.article.content.master.entity.Content
import com.jetbrains.life_science.article.content.master.factory.ContentFactory
import com.jetbrains.life_science.article.content.master.service.ContentInfo
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.article.section.service.SectionService
import com.jetbrains.life_science.exception.ContentNotFoundException
import com.jetbrains.life_science.exception.SectionNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContentVersionServiceImpl(
    val repository: ContentVersionRepository,
    val factory: ContentFactory
) : ContentVersionService {

    @Autowired
    lateinit var sectionService: SectionService

    override fun create(contentInfo: ContentInfo): Content {
        val content = factory.create(contentInfo)
        return repository.saveVersion(content)
    }

    override fun deleteAllBySectionId(sectionId: Long) {
        repository.deleteAllBySectionId(sectionId)
    }

    override fun findById(contentId: String?): Content {
        if (contentId == null) {
            throw ContentNotFoundException("Content not found by null id")
        }
        return repository.getVersion(contentId) ?: throw ContentNotFoundException("Content not found by id $contentId")
    }

    override fun update(contentInfo: ContentInfo): Content {
        val content = findById(contentInfo.id)
        factory.setParams(content, contentInfo)
        return repository.saveVersion(content)
    }

    override fun findAllBySectionId(sectionId: Long): List<Content> {
        return repository.findAllBySectionId(sectionId)
    }

    override fun delete(contentId: String) {
        repository.deleteById(contentId)
    }

}
