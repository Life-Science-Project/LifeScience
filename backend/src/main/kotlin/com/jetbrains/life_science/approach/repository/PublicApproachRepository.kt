package com.jetbrains.life_science.approach.repository

import com.jetbrains.life_science.approach.entity.PublicApproach
import org.springframework.data.jpa.repository.JpaRepository

interface PublicApproachRepository : JpaRepository<PublicApproach, Long>
