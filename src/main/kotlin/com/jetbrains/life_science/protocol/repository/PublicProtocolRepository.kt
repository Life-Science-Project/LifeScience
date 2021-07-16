package com.jetbrains.life_science.protocol.repository

import com.jetbrains.life_science.protocol.entity.PublicProtocol
import org.springframework.data.jpa.repository.JpaRepository

interface PublicProtocolRepository : JpaRepository<PublicProtocol, Long>
