package com.jetbrains.life_science.container.approach.service

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.container.approach.factory.DraftApproachFactory
import com.jetbrains.life_science.container.approach.repository.DraftApproachRepository
import com.jetbrains.life_science.exception.not_found.ApproachNotFoundException
import com.jetbrains.life_science.exception.request.RemoveOwnerFromParticipantsException
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.stereotype.Service

@Service
class DraftApproachServiceImpl(
    val repository: DraftApproachRepository,
    val factory: DraftApproachFactory
) : DraftApproachService {
    override fun get(id: Long): DraftApproach {
        return repository.findById(id).orElseThrow {
            ApproachNotFoundException("Draft approach with id $id is not found")
        }
    }

    override fun create(info: DraftApproachInfo): DraftApproach {
        val draftApproach = factory.create(info)
        return repository.save(draftApproach)
    }

    override fun update(info: DraftApproachInfo): DraftApproach {
        val draftApproach = get(info.id)
        factory.setParams(draftApproach, info)
        return repository.save(draftApproach)
    }

    override fun delete(draftApproachId: Long) {
        exists(draftApproachId)
        repository.deleteById(draftApproachId)
    }

    override fun addParticipant(draftApproachId: Long, user: Credentials): DraftApproach {
        val draftApproach = get(draftApproachId)
        if (user !in draftApproach.participants) {
            draftApproach.participants.add(user)
            repository.save(draftApproach)
        }
        return draftApproach
    }

    override fun removeParticipant(draftApproachId: Long, user: Credentials): DraftApproach {
        val draftApproach = get(draftApproachId)
        if (draftApproach.owner.id == user.id) {
            throw RemoveOwnerFromParticipantsException("Can't remove owner from approach participants")
        }
        if (user in draftApproach.participants) {
            draftApproach.participants.remove(user)
            repository.save(draftApproach)
        }
        return draftApproach
    }

    override fun hasParticipant(draftApproachId: Long, user: Credentials): Boolean {
        exists(draftApproachId)
        return repository.existsByIdAndParticipantsContains(draftApproachId, user)
    }

    override fun getAllByOwnerId(ownerId: Long): List<DraftApproach> {
        return repository.getAllByOwnerId(ownerId)
    }

    override fun addSection(id: Long, section: Section) {
        if (!hasSection(id, section)) {
            val draftApproach = get(id)
            draftApproach.sections.add(section)
            repository.save(draftApproach)
        }
    }

    override fun removeSection(id: Long, section: Section) {
        if (hasSection(id, section)) {
            val draftApproach = get(id)
            draftApproach.sections.removeAll { it.id == section.id }
            repository.save(draftApproach)
        }
    }

    override fun hasSection(id: Long, section: Section): Boolean {
        exists(id)
        return repository.existsByIdAndSectionsContains(id, section)
    }

    private fun exists(draftApproachId: Long) {
        if (!repository.existsById(draftApproachId)) {
            throw ApproachNotFoundException("Draft approach with id $draftApproachId is not found")
        }
    }
}
