package com.jetbrains.life_science.container.protocol.repository

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import org.springframework.data.jpa.repository.JpaRepository

interface PublicProtocolRepository : JpaRepository<PublicProtocol, Long>
