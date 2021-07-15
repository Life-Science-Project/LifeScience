package com.jetbrains.life_science.approach.draft.service

import com.jetbrains.life_science.approach.draft.entity.DraftApproach
import com.jetbrains.life_science.approach.draft.factory.DraftApproachFactory
import com.jetbrains.life_science.approach.draft.repository.DraftApproachRepository
import com.jetbrains.life_science.exception.approach.draft.DraftApproachNotFoundException
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
            DraftApproachNotFoundException(id)
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

    override fun delete(approachId: Long) {
        if (!repository.existsById(approachId)) {
            throw DraftApproachNotFoundException(approachId)
        }
        repository.deleteById(approachId)
    }

    override fun addParticipant(approachId: Long, user: Credentials): DraftApproach {
        val approach = get(approachId)
        if (user !in approach.participants) {
            approach.participants.add(user)
            repository.save(approach)
        }
        return approach
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
