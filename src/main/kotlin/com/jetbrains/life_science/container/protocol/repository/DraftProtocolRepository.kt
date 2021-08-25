package com.jetbrains.life_science.container.protocol.repository

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import org.springframework.data.jpa.repository.JpaRepository

interface DraftProtocolRepository : JpaRepository<DraftProtocol, Long> {
    fun getAllByOwnerId(id: Long): List<DraftProtocol>
}
