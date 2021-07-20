package com.jetbrains.life_science.approach.service

import com.jetbrains.life_science.approach.entity.DraftApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftApproachService {

    fun get(id: Long): DraftApproach

    fun create(info: DraftApproachInfo): DraftApproach

    fun update(info: DraftApproachInfo): DraftApproach

    fun delete(approachId: Long)

    fun addParticipant(approachId: Long, user: Credentials): DraftApproach

    fun removeParticipant(draftApproachId: Long, user: Credentials): DraftApproach

    fun addSection(draftApproachId: Long, section: Section): DraftApproach

    fun removeSection(draftApproachId: Long, section: Section): DraftApproach
}
