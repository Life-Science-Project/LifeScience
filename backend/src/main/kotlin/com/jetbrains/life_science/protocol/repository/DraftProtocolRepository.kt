package com.jetbrains.life_science.protocol.repository

import com.jetbrains.life_science.protocol.entity.DraftProtocol
import org.springframework.data.jpa.repository.JpaRepository

interface DraftProtocolRepository : JpaRepository<DraftProtocol, Long>
