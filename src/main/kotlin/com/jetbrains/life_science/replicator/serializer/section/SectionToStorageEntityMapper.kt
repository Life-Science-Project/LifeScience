package com.jetbrains.life_science.replicator.serializer.section

import com.jetbrains.life_science.content.publish.repository.ContentRepository
import com.jetbrains.life_science.replicator.enities.SectionStorageEntity
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component

@Component
class SectionToStorageEntityMapper(
    private val contentRepository: ContentRepository
) {

    fun getStorageEntities(sections: List<Section>): List<SectionStorageEntity> {
        return sections.map { mapOne(it) }
    }

    fun mapOne(section: Section): SectionStorageEntity {
        return SectionStorageEntity(
            name = section.name,
            hidden = section.hidden,
            content = contentRepository.findBySectionId(section.id)?.text
        )
    }
}
