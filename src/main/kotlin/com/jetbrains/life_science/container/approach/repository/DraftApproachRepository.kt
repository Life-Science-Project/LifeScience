package com.jetbrains.life_science.container.approach.repository

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.section.entity.Section
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.data.jpa.repository.JpaRepository

interface DraftApproachRepository : JpaRepository<DraftApproach, Long> {
    fun existsByIdAndSectionsContains(id: Long, section: Section): Boolean
    fun existsByIdAndParticipantsContains(id: Long, credentials: Credentials): Boolean
    fun getAllByOwnerId(ownerId: Long): List<DraftApproach>
}
