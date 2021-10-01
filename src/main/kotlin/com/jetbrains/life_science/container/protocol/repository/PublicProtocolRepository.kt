package com.jetbrains.life_science.container.protocol.repository

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.section.entity.Section
import org.springframework.data.jpa.repository.JpaRepository

interface PublicProtocolRepository : JpaRepository<PublicProtocol, Long> {
    fun existsByIdAndSectionsContains(id: Long, section: Section): Boolean
    fun existsByIdAndApproachId(id: Long, approachId: Long): Boolean
    fun getAllByOwnerId(id: Long): List<PublicProtocol>
}
