package com.jetbrains.life_science.user.position.repository

import com.jetbrains.life_science.user.position.entity.Position
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PositionRepository : JpaRepository<Position, Long>
