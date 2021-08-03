package com.jetbrains.life_science.replicator.serializer.approach

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.repository.PublicApproachRepository
import com.jetbrains.life_science.replicator.enities.ApproachStorageEntity
import com.jetbrains.life_science.replicator.serializer.protocol.ProtocolToStorageEntityMapper
import com.jetbrains.life_science.replicator.serializer.section.SectionToStorageEntityMapper
import org.springframework.stereotype.Component

@Component
class ApproachToStorageEntityMapper(
    val protocolToStorageEntityMapper: ProtocolToStorageEntityMapper,
    val sectionToStorageEntityMapper: SectionToStorageEntityMapper,
    val publicApproachRepository: PublicApproachRepository
) {

    fun getStorageEntities(): List<ApproachStorageEntity> {
        return publicApproachRepository.findAll().map { mapOne(it) }
    }

    fun mapOne(approach: PublicApproach): ApproachStorageEntity {
        val section = sectionToStorageEntityMapper.getStorageEntities(approach.sections)
        val protocols = protocolToStorageEntityMapper.getStorageEntities(approach.protocols)
        return ApproachStorageEntity(
            name = approach.name,
            sections = section,
            protocols = protocols,
            categories = approach.categories.map { it.id },
            aliases = approach.aliases
        )
    }
}
