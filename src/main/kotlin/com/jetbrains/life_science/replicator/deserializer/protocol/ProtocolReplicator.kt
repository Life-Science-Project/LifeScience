package com.jetbrains.life_science.replicator.deserializer.protocol

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.container.protocol.repository.PublicProtocolRepository
import com.jetbrains.life_science.container.protocol.search.repository.ProtocolSearchUnitRepository
import com.jetbrains.life_science.container.protocol.search.service.ProtocolSearchUnitService
import com.jetbrains.life_science.replicator.deserializer.credentials.CredentialsReplicator
import com.jetbrains.life_science.replicator.enities.ProtocolStorageEntity
import com.jetbrains.life_science.replicator.deserializer.section.SectionReplicator
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class ProtocolReplicator(
    private val publicProtocolRepository: PublicProtocolRepository,
    private val publicProtocolSearchUnitService: ProtocolSearchUnitService,
    private val protocolSearchUnitRepository: ProtocolSearchUnitRepository,
    private val sectionReplicator: SectionReplicator,
    private val credentialsReplicator: CredentialsReplicator,
    private val entityManager: EntityManager
) {

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery("alter sequence public_protocol_seq restart with 1;")
            .executeUpdate()
        publicProtocolRepository.deleteAll()
        protocolSearchUnitRepository.deleteAll()
    }

    @Transactional
    fun replicateData(approach: PublicApproach, data: List<ProtocolStorageEntity>): List<PublicProtocol> {
        return data.map { replicateOne(approach, it) }
    }

    private fun replicateOne(approach: PublicApproach, data: ProtocolStorageEntity): PublicProtocol {
        val sections = sectionReplicator.replicateData(data.sections)
        var protocol = makeProtocol(data, approach, sections)
        protocol = publicProtocolRepository.save(protocol)
        publicProtocolSearchUnitService.createSearchUnit(protocol)
        return protocol
    }

    private fun makeProtocol(
        data: ProtocolStorageEntity,
        approach: PublicApproach,
        sections: List<Section>
    ) = PublicProtocol(
        id = 0,
        name = data.name,
        approach = approach,
        sections = sections.toMutableList(),
        owner = credentialsReplicator.admin,
        coAuthors = mutableListOf(credentialsReplicator.admin),
        rating = 1
    )
}
