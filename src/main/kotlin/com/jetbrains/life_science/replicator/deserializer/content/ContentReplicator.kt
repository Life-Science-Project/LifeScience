package com.jetbrains.life_science.replicator.deserializer.content

import com.jetbrains.life_science.content.publish.entity.Content
import com.jetbrains.life_science.content.publish.repository.ContentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ContentReplicator(
    val contentRepository: ContentRepository
) {

    fun deleteAll() {
        contentRepository.deleteAll()
    }

    @Transactional
    fun replicateData(sectionId: Long, text: String) {
        val content = Content(
            sectionId = sectionId,
            text = text,
            tags = mutableListOf(),
            references = mutableListOf()
        )
        contentRepository.save(content)
    }
}
