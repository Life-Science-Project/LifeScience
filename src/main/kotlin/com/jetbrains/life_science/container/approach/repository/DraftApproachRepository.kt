package com.jetbrains.life_science.container.approach.repository

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import org.springframework.data.jpa.repository.JpaRepository

interface DraftApproachRepository : JpaRepository<DraftApproach, Long>
