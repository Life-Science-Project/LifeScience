package com.jetbrains.life_science.replicator.serializer.protocol

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.entity.Protocol
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.container.protocol.repository.DraftProtocolRepository
import com.jetbrains.life_science.replicator.enities.ProtocolStorageEntity
import com.jetbrains.life_science.replicator.serializer.section.SectionToStorageEntityMapper
import org.springframework.stereotype.Component

@Component
class ProtocolToStorageEntityMapper(
    private val sectionToStorageEntityMapper: SectionToStorageEntityMapper,
    private val draftProtocolRepository: DraftProtocolRepository,
) {

    fun getPublicStorageEntities(protocols: List<PublicProtocol>): List<ProtocolStorageEntity> {
        return protocols.map { mapPublicOne(it) }
    }

    fun getDraftStorageEntities(): List<ProtocolStorageEntity> {
        return draftProtocolRepository.findAll().map { mapDraftOne(it) }
    }

    fun mapDraftOne(protocol: DraftProtocol): ProtocolStorageEntity = mapOne(protocol, null)

    fun mapPublicOne(protocol: PublicProtocol): ProtocolStorageEntity = mapOne(protocol, protocol.rating)

    fun mapOne(protocol: Protocol, rating: Long?): ProtocolStorageEntity {
        val section = sectionToStorageEntityMapper.getStorageEntities(protocol.sections.sortedBy { it.order })
        return ProtocolStorageEntity(
            name = protocol.name,
            sections = section,
            ownerId = protocol.owner.id,
            rating = rating,
            approachId = protocol.approach.id
        )
    }
}
