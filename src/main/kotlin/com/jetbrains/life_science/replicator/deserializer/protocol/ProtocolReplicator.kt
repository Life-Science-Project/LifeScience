package com.jetbrains.life_science.replicator.deserializer.protocol

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.container.protocol.repository.DraftProtocolRepository
import com.jetbrains.life_science.container.protocol.repository.PublicProtocolRepository
import com.jetbrains.life_science.container.protocol.search.repository.ProtocolSearchUnitRepository
import com.jetbrains.life_science.container.protocol.search.service.ProtocolSearchUnitService
import com.jetbrains.life_science.replicator.deserializer.credentials.CredentialsReplicator
import com.jetbrains.life_science.replicator.enities.ProtocolStorageEntity
import com.jetbrains.life_science.replicator.deserializer.section.SectionReplicator
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class ProtocolReplicator(
    private val publicProtocolRepository: PublicProtocolRepository,
    private val draftProtocolRepository: DraftProtocolRepository,
    private val publicProtocolSearchUnitService: ProtocolSearchUnitService,
    private val publicApproachService: PublicApproachService,
    private val protocolSearchUnitRepository: ProtocolSearchUnitRepository,
    private val sectionReplicator: SectionReplicator,
    private val credentialsReplicator: CredentialsReplicator,
    private val credentialsRepository: CredentialsRepository,
    private val entityManager: EntityManager
) {

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery("alter sequence public_protocol_seq restart with 1;")
            .executeUpdate()
        entityManager.createNativeQuery("alter sequence draft_protocol_seq restart with 1;")
            .executeUpdate()
        draftProtocolRepository.deleteAll()
        publicProtocolRepository.deleteAll()
        protocolSearchUnitRepository.deleteAll()
    }

    @Transactional
    fun replicatePublicProtocolData(approach: PublicApproach, data: List<ProtocolStorageEntity>): List<PublicProtocol> {
        val result = data.map { replicatePublicOne(approach, it) }
        entityManager.flush()
        return result
    }

    @Transactional
    fun replicateDraftProtocolData(data: List<ProtocolStorageEntity>): List<DraftProtocol> {
        val result = data.map { replicateDraftOne(it) }
        entityManager.flush()
        return result
    }

    private fun replicateDraftOne(data: ProtocolStorageEntity): DraftProtocol {
        val sections = sectionReplicator.replicateData(data.sections)
        val approach = publicApproachService.get(data.approachId)
        val protocol = makeDraftProtocol(data, approach, sections)
        return draftProtocolRepository.save(protocol)
    }

    private fun replicatePublicOne(approach: PublicApproach, data: ProtocolStorageEntity): PublicProtocol {
        val sections = sectionReplicator.replicateData(data.sections)
        var protocol = makePublicProtocol(data, approach, sections)
        protocol = publicProtocolRepository.save(protocol)
        publicProtocolSearchUnitService.createSearchUnit(protocol)
        return protocol
    }

    private fun makePublicProtocol(
        data: ProtocolStorageEntity,
        approach: PublicApproach,
        sections: List<Section>
    ): PublicProtocol {
        val protocol = PublicProtocol(
            id = 0,
            name = data.name,
            approach = approach,
            sections = sections.toMutableList(),
            owner = credentialsRepository
                .findById(data.ownerId)
                .orElse(credentialsReplicator.admin),
            coAuthors = mutableListOf(),
            rating = data.rating ?: 0
        )
        protocol.coAuthors.add(protocol.owner)
        return protocol
    }

    private fun makeDraftProtocol(
        data: ProtocolStorageEntity,
        approach: PublicApproach,
        sections: List<Section>
    ): DraftProtocol {
        val protocol = DraftProtocol(
            id = 0,
            name = data.name,
            approach = approach,
            sections = sections.toMutableList(),
            owner = credentialsRepository
                .findById(data.ownerId)
                .orElse(credentialsReplicator.admin),
            participants = mutableListOf()
        )
        protocol.participants.add(protocol.owner)
        return protocol
    }
}
