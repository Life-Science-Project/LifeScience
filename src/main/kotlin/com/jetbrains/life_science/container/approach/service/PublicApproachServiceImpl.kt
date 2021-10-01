package com.jetbrains.life_science.container.approach.service

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.approach.factory.PublicApproachFactory
import com.jetbrains.life_science.container.approach.repository.PublicApproachRepository
import com.jetbrains.life_science.container.approach.search.service.ApproachSearchUnitService
import com.jetbrains.life_science.exception.not_found.ApproachNotFoundException
import com.jetbrains.life_science.section.entity.Section
import org.springframework.stereotype.Service

@Service
class PublicApproachServiceImpl(
    val repository: PublicApproachRepository,
    val factory: PublicApproachFactory,
    val searchUnitService: ApproachSearchUnitService
) : PublicApproachService {
    override fun get(id: Long): PublicApproach {
        return repository.findById(id).orElseThrow {
            ApproachNotFoundException("Public approach with id $id is not found")
        }
    }

    override fun getAllByOwnerId(ownerId: Long): List<PublicApproach> {
        return repository.getAllByOwnerId(ownerId)
    }

    override fun create(approach: DraftApproach): PublicApproach {
        val publicApproach = factory.create(approach)
        val savedPublicApproach = repository.save(publicApproach)
        searchUnitService.createSearchUnit(savedPublicApproach)
        return savedPublicApproach
    }

    override fun addSection(id: Long, section: Section) {
        if (!hasSection(id, section)) {
            val publicApproach = get(id)
            publicApproach.sections.add(section)
            repository.save(publicApproach)
        }
    }

    override fun removeSection(id: Long, section: Section) {
        if (hasSection(id, section)) {
            val publicApproach = get(id)
            publicApproach.sections.removeAll { it.id == section.id }
            repository.save(publicApproach)
        }
    }

    override fun hasSection(id: Long, section: Section): Boolean {
        if (!repository.existsById(id)) {
            throw ApproachNotFoundException("Public approach with id $id is not found")
        }
        return repository.existsByIdAndSectionsContains(id, section)
    }
}
