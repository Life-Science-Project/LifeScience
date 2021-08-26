package com.jetbrains.life_science.container.protocol.repository

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.data.jpa.repository.JpaRepository

interface DraftProtocolRepository : JpaRepository<DraftProtocol, Long> {
    fun getAllByOwnerId(id: Long): List<DraftProtocol>
    fun participantsContains(id: Long, credentials: Credentials): Boolean
}
