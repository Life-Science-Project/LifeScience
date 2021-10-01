package com.jetbrains.life_science.replicator.deserializer.approach

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.repository.DraftApproachRepository
import com.jetbrains.life_science.container.approach.repository.PublicApproachRepository
import com.jetbrains.life_science.container.approach.search.repository.ApproachSearchUnitRepository
import com.jetbrains.life_science.container.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.replicator.deserializer.credentials.CredentialsReplicator
import com.jetbrains.life_science.replicator.enities.ApproachStorageEntity
import com.jetbrains.life_science.replicator.deserializer.protocol.ProtocolReplicator
import com.jetbrains.life_science.replicator.deserializer.section.SectionReplicator
import com.jetbrains.life_science.user.credentials.repository.CredentialsRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class ApproachReplicator(
    private val protocolReplicator: ProtocolReplicator,
    private val sectionReplicator: SectionReplicator,
    private val publicApproachRepository: PublicApproachRepository,
    private val draftApproachRepository: DraftApproachRepository,
    private val publicApproachSearchUnitService: ApproachSearchUnitService,
    private val approachSearchUnitRepository: ApproachSearchUnitRepository,
    private val categoryService: CategoryService,
    private val credentialsReplicator: CredentialsReplicator,
    private val credentialsRepository: CredentialsRepository,
    private val entityManager: EntityManager
) {

    @Transactional
    fun deleteAllPublic() {
        entityManager.createNativeQuery("alter sequence public_approach_seq restart with 1;")
            .executeUpdate()
        publicApproachRepository.deleteAll()
        approachSearchUnitRepository.deleteAll()
    }

    @Transactional
    fun deleteAllDraft() {
        entityManager.createNativeQuery("alter sequence draft_approach_seq restart with 1;")
            .executeUpdate()
        draftApproachRepository.deleteAll()
    }

    @Transactional
    fun replicateData(publicData: List<ApproachStorageEntity>, draftData: List<ApproachStorageEntity>) {
        publicData.forEach { makePublicOne(it) }
        draftData.forEach { makeDraftOne(it) }
        entityManager.flush()
    }

    fun makePublicOne(storageEntity: ApproachStorageEntity) {
        val categories = storageEntity.categories.map { categoryService.getById(it) }.toMutableList()
        var approach = makePublicApproach(storageEntity, categories)
        approach = publicApproachRepository.save(approach)
        publicApproachSearchUnitService.createSearchUnit(approach)
        val protocols = protocolReplicator.replicatePublicProtocolData(approach, storageEntity.protocols)
        approach.protocols.addAll(protocols)
        approach = publicApproachRepository.save(approach)
        categories.forEach { it.approaches.add(approach) }
    }

    fun makeDraftOne(storageEntity: ApproachStorageEntity) {
        val categories = storageEntity.categories.map { categoryService.getById(it) }.toMutableList()
        val approach = makeDraftApproach(storageEntity, categories)
        draftApproachRepository.save(approach)
    }

    fun makePublicApproach(storageEntity: ApproachStorageEntity, categories: MutableList<Category>): PublicApproach {
        val sections = sectionReplicator.replicateData(storageEntity.sections)
        val approach = PublicApproach(
            id = 0,
            name = storageEntity.name,
            sections = sections.toMutableList(),
            owner = credentialsRepository
                .findById(storageEntity.ownerId)
                .orElse(credentialsReplicator.admin),
            tags = mutableListOf(),
            creationDate = LocalDateTime.parse(storageEntity.creationDateTime),
            coAuthors = mutableListOf(),
            protocols = mutableListOf(),
            categories = categories,
            aliases = storageEntity.aliases
        )
        approach.coAuthors.add(approach.owner)
        return approach
    }

    fun makeDraftApproach(storageEntity: ApproachStorageEntity, categories: MutableList<Category>): DraftApproach {
        val sections = sectionReplicator.replicateData(storageEntity.sections)
        val approach = DraftApproach(
            id = 0,
            name = storageEntity.name,
            sections = sections.toMutableList(),
            owner = credentialsRepository
                .findById(storageEntity.ownerId)
                .orElse(credentialsReplicator.admin),
            tags = mutableListOf(),
            creationDate = LocalDateTime.parse(storageEntity.creationDateTime),
            participants = mutableListOf(),
            categories = categories,
            aliases = storageEntity.aliases
        )
        approach.participants.add(approach.owner)
        return approach
    }
}
