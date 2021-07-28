package com.jetbrains.life_science.container.approach.repository

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import org.springframework.data.jpa.repository.JpaRepository

interface PublicApproachRepository : JpaRepository<PublicApproach, Long>
