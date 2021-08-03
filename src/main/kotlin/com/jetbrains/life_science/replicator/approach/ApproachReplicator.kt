package com.jetbrains.life_science.replicator.approach

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.repository.PublicApproachRepository
import com.jetbrains.life_science.container.approach.search.repository.ApproachSearchUnitRepository
import com.jetbrains.life_science.container.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.replicator.credentials.CredentialsReplicator
import com.jetbrains.life_science.replicator.enities.ApproachStorageEntity
import com.jetbrains.life_science.replicator.protocol.ProtocolReplicator
import com.jetbrains.life_science.replicator.section.SectionReplicator
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Component
class ApproachReplicator(
    private val protocolReplicator: ProtocolReplicator,
    private val sectionReplicator: SectionReplicator,
    private val publicApproachRepository: PublicApproachRepository,
    private val publicApproachSearchUnitService: ApproachSearchUnitService,
    private val approachSearchUnitRepository: ApproachSearchUnitRepository,
    private val categoryService: CategoryService,
    private val credentialsReplicator: CredentialsReplicator,
    private val entityManager: EntityManager
) {

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery("ALTER SEQUENCE public_approach_seq RESTART WITH 1;")
            .executeUpdate()
        publicApproachRepository.deleteAll()
        approachSearchUnitRepository.deleteAll()
    }

    @Transactional
    fun replicateData(data: List<ApproachStorageEntity>) {
        data.forEach { makeOne(it) }
    }

    fun makeOne(storageEntity: ApproachStorageEntity) {
        val categories = storageEntity.categories.map { categoryService.getCategory(it) }.toMutableList()
        var approach = makeApproach(storageEntity, categories)
        approach = publicApproachRepository.save(approach)
        publicApproachSearchUnitService.createSearchUnit(approach)
        val protocols = protocolReplicator.replicateData(approach, storageEntity.protocols)
        approach.protocols.addAll(protocols)
        approach = publicApproachRepository.save(approach)
        categories.forEach { it.approaches.add(approach) }
    }

    fun makeApproach(storageEntity: ApproachStorageEntity, categories: MutableList<Category>): PublicApproach {
        val sections = sectionReplicator.replicateData(storageEntity.sections)
        return PublicApproach(
            id = 0,
            name = storageEntity.name,
            sections = sections.toMutableList(),
            owner = credentialsReplicator.admin,
            tags = mutableListOf(),
            creationDate = creationDate,
            coAuthors = mutableListOf(credentialsReplicator.admin),
            protocols = mutableListOf(),
            categories = categories,
            aliases = storageEntity.aliases
        )
    }

    private val creationDate = LocalDateTime.of(2021, 6, 1, 0, 0)
}
