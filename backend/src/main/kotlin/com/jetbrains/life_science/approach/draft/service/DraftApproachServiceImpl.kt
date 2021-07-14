package com.jetbrains.life_science.approach.draft.service

import com.jetbrains.life_science.approach.draft.entity.DraftApproach
import com.jetbrains.life_science.approach.draft.factory.DraftApproachFactory
import com.jetbrains.life_science.approach.draft.repository.DraftApproachRepository
import com.jetbrains.life_science.controller.approach.draft.dto.DraftCategoryCreationDTOToInfoAdapter
import com.jetbrains.life_science.exception.approach.draft.DraftApproachNotFoundException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.stereotype.Service

@Service
class DraftApproachServiceImpl(
    val repository: DraftApproachRepository,
    val factory: DraftApproachFactory
) : DraftApproachService {

    override fun create(info: DraftCategoryCreationDTOToInfoAdapter): DraftApproach {
        val approach = factory.create(info)
        return repository.save(approach)
    }

    override fun addParticipant(approach: DraftApproach, userCredentials: Credentials) {
        approach.participants += userCredentials
        repository.save(approach)
    }

    override fun delete(approach: DraftApproach) {
        repository.delete(approach)
    }

    override fun getApproach(id: Long): DraftApproach {
        return safeGet(id) ?: throw DraftApproachNotFoundException(id)
    }

    private fun safeGet(id: Long): DraftApproach? {
        return repository.findDraftApproachById(id)
    }
}
