package com.jetbrains.life_science.replicator.serializer.approach

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.repository.DraftApproachRepository
import com.jetbrains.life_science.replicator.enities.ApproachStorageEntity
import com.jetbrains.life_science.replicator.serializer.section.SectionToStorageEntityMapper
import org.springframework.stereotype.Component

@Component
class DraftApproachToStorageEntityMapper(
    private val sectionToStorageEntityMapper: SectionToStorageEntityMapper,
    private val draftApproachRepository: DraftApproachRepository
) {
    fun getStorageEntities(categoriesIdsMap: Map<Long, Long>): List<ApproachStorageEntity> {
        return draftApproachRepository.findAll().map { mapOne(it, categoriesIdsMap) }
    }

    fun mapOne(approach: DraftApproach, categoriesIdsMap: Map<Long, Long>): ApproachStorageEntity {
        val section = sectionToStorageEntityMapper.getStorageEntities(approach.sections.sortedBy { it.order })
        return ApproachStorageEntity(
            name = approach.name,
            sections = section,
            protocols = listOf(),
            categories = approach.categories.map { categoriesIdsMap[it.id]!! },
            aliases = approach.aliases,
            creationDateTime = approach.creationDate.toString(),
            ownerId = approach.owner.id
        )
    }
}
