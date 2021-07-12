package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.approach.factory.DraftApproachFactory
import com.jetbrains.life_science.approach.repository.DraftApproachRepository
import com.jetbrains.life_science.exception.not_found.DraftApproachNotFoundException
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
            DraftApproachNotFoundException("Draft approach with id $id is not found")
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
        if (!repository.existsById(draftApproachId)) {
            throw DraftApproachNotFoundException("Draft approach with id $draftApproachId is not found")
        }
        repository.deleteById(draftApproachId)
    }

    override fun addParticipant(draftApproachId: Long, user: Credentials): DraftApproach {
        val draftApproach = get(draftApproachId)
        if (!draftApproach.participants.contains(user)) {
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
        if (draftApproach.participants.contains(user)) {
            draftApproach.participants.remove(user)
            repository.save(draftApproach)
        }
        return draftApproach
    }

    override fun addSection(draftApproachId: Long, section: Section): DraftApproach {
        val draftApproach = get(draftApproachId)
        if (!draftApproach.sections.any { it.id == section.id }) {
            draftApproach.sections.add(section)
            repository.save(draftApproach)
        }
        return draftApproach
    }

    override fun removeSection(draftApproachId: Long, section: Section): DraftApproach {
        val draftApproach = get(draftApproachId)
        draftApproach.sections.removeAll { it.id == section.id }
        repository.save(draftApproach)
        return draftApproach
    }
}
