package com.jetbrains.life_science.replicator.serializer.approach

import com.jetbrains.life_science.container.approach.entity.Approach
import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.repository.DraftApproachRepository
import com.jetbrains.life_science.container.approach.repository.PublicApproachRepository
import com.jetbrains.life_science.replicator.enities.ApproachStorageEntity
import com.jetbrains.life_science.replicator.enities.ProtocolStorageEntity
import com.jetbrains.life_science.replicator.serializer.protocol.ProtocolToStorageEntityMapper
import com.jetbrains.life_science.replicator.serializer.section.SectionToStorageEntityMapper
import org.springframework.stereotype.Component

@Component
class ApproachToStorageEntityMapper(
    private val protocolToStorageEntityMapper: ProtocolToStorageEntityMapper,
    private val sectionToStorageEntityMapper: SectionToStorageEntityMapper,
    private val publicApproachRepository: PublicApproachRepository,
    private val draftApproachRepository: DraftApproachRepository
) {

    fun getPublicApproachStorageEntities(categoriesIdsMap: Map<Long, Long>): List<ApproachStorageEntity> {
        return publicApproachRepository.findAll().map { mapPublicOne(it, categoriesIdsMap) }
    }

    fun getDraftApproachStorageEntities(categoriesIdsMap: Map<Long, Long>): List<ApproachStorageEntity> {
        return draftApproachRepository.findAll().map { mapDraftOne(it, categoriesIdsMap) }
    }

    fun mapPublicOne(approach: PublicApproach, categoriesIdsMap: Map<Long, Long>): ApproachStorageEntity {
        val protocols = protocolToStorageEntityMapper.getPublicStorageEntities(approach.protocols)
        return mapOne(approach, categoriesIdsMap, protocols)
    }

    fun mapDraftOne(approach: DraftApproach, categoriesIdsMap: Map<Long, Long>): ApproachStorageEntity {
        return mapOne(approach, categoriesIdsMap, listOf())
    }

    fun mapOne(
        approach: Approach,
        categoriesIdsMap: Map<Long, Long>,
        protocols: List<ProtocolStorageEntity>
    ): ApproachStorageEntity {
        val section = sectionToStorageEntityMapper.getStorageEntities(approach.sections.sortedBy { it.order })
        return ApproachStorageEntity(
            name = approach.name,
            sections = section,
            protocols = protocols,
            categories = approach.categories.map { categoriesIdsMap[it.id]!! },
            aliases = approach.aliases,
            creationDateTime = approach.creationDate.toString(),
            ownerId = approach.owner.id
        )
    }
}
