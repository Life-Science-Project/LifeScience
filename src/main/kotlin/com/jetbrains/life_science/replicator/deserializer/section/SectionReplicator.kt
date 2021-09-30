package com.jetbrains.life_science.replicator.deserializer.section

import com.jetbrains.life_science.replicator.deserializer.content.ContentReplicator
import com.jetbrains.life_science.replicator.enities.SectionStorageEntity
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.section.repository.SectionRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class SectionReplicator(
    private val sectionRepository: SectionRepository,
    private val contentReplicator: ContentReplicator,
    private val entityManager: EntityManager
) {

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery("alter sequence section_seq restart with 1;")
            .executeUpdate()
        sectionRepository.deleteAll()
    }

    @Transactional
    fun replicateData(sections: List<SectionStorageEntity>): List<Section> {
        return sections.mapIndexed { index, sectionStorageEntity -> replicateOne(index, sectionStorageEntity) }
    }

    private fun replicateOne(index: Int, sectionStorageEntity: SectionStorageEntity): Section {
        var section = makeSection(sectionStorageEntity, index)
        section = sectionRepository.save(section)
        sectionStorageEntity.content?.let { content ->
            contentReplicator.replicateData(section.id, content)
        }
        return section
    }

    private fun makeSection(
        sectionStorageEntity: SectionStorageEntity,
        index: Int
    ) = Section(
        id = 0,
        name = sectionStorageEntity.name,
        hidden = sectionStorageEntity.hidden,
        published = true,
        order = index
    )
}
