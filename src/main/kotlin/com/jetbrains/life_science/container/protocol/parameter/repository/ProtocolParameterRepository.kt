package com.jetbrains.life_science.container.protocol.parameter.repository

import com.jetbrains.life_science.container.protocol.parameter.entity.ProtocolParameter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProtocolParameterRepository : JpaRepository<ProtocolParameter, Long>
