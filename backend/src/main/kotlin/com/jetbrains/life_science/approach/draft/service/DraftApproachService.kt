package com.jetbrains.life_science.approach.draft.service

import com.jetbrains.life_science.approach.draft.entity.DraftApproach
import com.jetbrains.life_science.controller.approach.draft.dto.DraftCategoryCreationDTOToInfoAdapter
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftApproachService {

    fun getApproach(id: Long): DraftApproach

    fun create(info: DraftCategoryCreationDTOToInfoAdapter): DraftApproach

    fun addParticipant(approach: DraftApproach, userCredentials: Credentials)

    fun delete(approach: DraftApproach)
}