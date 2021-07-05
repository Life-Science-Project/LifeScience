package com.jetbrains.life_science.approach.repository

import com.jetbrains.life_science.approach.entity.Approach
import org.springframework.data.jpa.repository.JpaRepository

interface ApproachRepository: JpaRepository<Approach, Long>
