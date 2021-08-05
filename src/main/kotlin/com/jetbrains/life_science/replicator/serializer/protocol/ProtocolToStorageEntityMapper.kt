package com.jetbrains.life_science.replicator.serializer.protocol

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.replicator.enities.ProtocolStorageEntity
import com.jetbrains.life_science.replicator.serializer.section.SectionToStorageEntityMapper
import org.springframework.stereotype.Component

@Component
class ProtocolToStorageEntityMapper(
    val sectionToStorageEntityMapper: SectionToStorageEntityMapper
) {

    fun getStorageEntities(protocols: List<PublicProtocol>): List<ProtocolStorageEntity> {
        return protocols.map { mapOne(it) }
    }

    fun mapOne(protocol: PublicProtocol): ProtocolStorageEntity {
        val section = sectionToStorageEntityMapper.getStorageEntities(protocol.sections)
        return ProtocolStorageEntity(
            name = protocol.name,
            sections = section
        )
    }
}
