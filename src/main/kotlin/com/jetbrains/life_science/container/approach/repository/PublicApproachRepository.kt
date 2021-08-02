package com.jetbrains.life_science.container.approach.repository

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section
import org.springframework.data.jpa.repository.JpaRepository

interface PublicApproachRepository : JpaRepository<PublicApproach, Long> {
    fun existsByIdAndSectionsContains(id: Long, section: Section): Boolean
    fun existsByIdAndProtocolsContains(approachId: Long, protocol: PublicProtocol): Boolean
}
