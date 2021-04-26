package com.jetbrains.life_science.article.content.version.service

import com.jetbrains.life_science.article.content.publish.entity.Content
import com.jetbrains.life_science.article.content.publish.factory.ContentFactory
import com.jetbrains.life_science.article.content.publish.service.ContentInfo
import com.jetbrains.life_science.article.content.version.repository.ContentVersionRepository
import com.jetbrains.life_science.exception.ContentNotFoundException
import org.springframework.stereotype.Service

@Service
class ContentVersionServiceImpl(
    val repository: ContentVersionRepository,
    val factory: ContentFactory
) : ContentVersionService {

    override fun create(contentInfo: ContentInfo): Content {
        val content = factory.create(contentInfo)
        return repository.save(content)
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

    override fun update(contentInfo: ContentInfo): Content {
        val content = findById(contentInfo.id)
        factory.setParams(content, contentInfo)
        return repository.save(content)
    }

    override fun findBySectionId(sectionId: Long): Content? {
        return repository.findBySectionId(sectionId)
    }

    override fun delete(contentId: String) {
        repository.deleteById(contentId)
    }
}
