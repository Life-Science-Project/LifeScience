package com.jetbrains.life_science.content.publish.service

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.publish.factory.ContentFactory
import com.jetbrains.life_science.content.publish.repository.ContentRepository
import com.jetbrains.life_science.content.version.service.ContentVersionService
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

    override fun delete(id: String) {
        checkContentExists(id)
        repository.deleteById(id)
    }

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
        checkContentExists(contentId)
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

    private fun checkContentExists(id: String) {
        if (!repository.existsById(id)) {
            throw ContentNotFoundException("Content not found by id: $id")
        }
    }
}
