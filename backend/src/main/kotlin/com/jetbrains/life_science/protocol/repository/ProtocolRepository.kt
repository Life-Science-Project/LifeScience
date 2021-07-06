package com.jetbrains.life_science.protocol.repository

import com.jetbrains.life_science.protocol.entity.Protocol
import org.springframework.data.jpa.repository.JpaRepository

interface ProtocolRepository : JpaRepository<Protocol, Long>
