package com.jetbrains.life_science.container.approach.service

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftApproachService {

    fun get(id: Long): DraftApproach

    fun create(info: DraftApproachInfo): DraftApproach

    fun update(info: DraftApproachInfo): DraftApproach

    fun delete(draftApproachId: Long)

    fun addParticipant(draftApproachId: Long, user: Credentials): DraftApproach

    fun removeParticipant(draftApproachId: Long, user: Credentials): DraftApproach

    fun addSection(draftApproachId: Long, section: Section): DraftApproach

    fun removeSection(draftApproachId: Long, section: Section): DraftApproach
}